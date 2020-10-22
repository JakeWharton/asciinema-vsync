# Asciinema Vsync

A tool for processing the JSON output of [Asciinema](https://asciinema.org/) in order to batch
together its captured events (similar to how the vsync pulse is used to batch GUI changes).
This helps reduce flicker of the final rendering when targeting formats like SVG or GIF.

|Before|After|
|------|-----|
|<img src="src/test/fixtures/jest/input.svg">|<img src="src/test/fixtures/jest/output.svg">|

The differences here are extremely subtle! You can occasionally see flickers in the "Before"
image as it displays a partially-updated output which are not present in the "After".

The JSON input used to create those two images should further highlight the behavior of this tool:

**Before:**
```json
{"version": 2, "width": 247, "height": 50, "timestamp": 1603340113, "env": {"SHELL": "/usr/local/bin/bash", "TERM": "xterm-256color"}}
[0.232306, "o", "Tests: 0 to"]
[0.232908, "o", "tal\u001b[K\r\nTime:  0s     \u001b[K\r\n"]
[0.294751, "o", "\u001b[F\u001b[F"]
[0.2948, "o", "\u001b[33;43"]
[0.294816, "o", "m"]
[0.295049, "o", "_\u001b[30mRU"]
[0.295122, "o", "NS \u001b[39;49m tests"]
[0.295202, "o", "/\u001b[1mlo"]
[0.295269, "o", "gin.kt\u001b[2"]
[0.295343, "o", "2m    "]
[0.295443, "o", "      \u001b"]
[0.29554, "o", "[K\r\n\u001b"]
[0.295652, "o", "[33;43m_"]
[0.295732, "o", "\u001b[30mRUNS "]
[0.295866, "o", "\u001b[39;49m tests"]
[0.29594, "o", "/\u001b[1msignup"]
[0.295958, "o", ".k"]
[0.295972, "o", "t\u001b"]
```

The output in this "Before" JSON is fragmented across multiple commands which occur
fractions of a second apart. This is despite the fact that the originating program issued the
output as large, single writes.

**After:**
```json
{"version": 2, "width": 247, "height": 50, "timestamp": 1603340113, "env": {"SHELL": "/usr/local/bin/bash", "TERM": "xterm-256color"}}
[0.232306,"o","Tests: 0 total\u001b[K\r\nTime:  0s     \u001b[K\r\n"]
[0.294751,"o","\u001b[F\u001b[F\u001b[33;43m_\u001b[30mRUNS \u001b[39;49m tests/\u001b[1mlogin.kt\u001b[22m          \u001b[K\r\n\u001b[33;43m_\u001b[30mRUNS \u001b[39;49m tests/\u001b[1msignup.kt\u001b[22m         \u001b[K\r\n\u001b[33;43m_\u001b[30mRUNS \u001b[39;49m tests/\u001b[1mforgot-password.kt\u001b[22m\u001b[K\r\n\u001b[33;43m_\u001b[30mRUNS \u001b[39;49m tests/\u001b[1mreset-password.kt\u001b[22m \u001b[K\r\n                               \u001b[K\r\nTests: 4 total                 \u001b[K\r\nTime:  0s                      \u001b[K\r\n"]
[1.265236,"o","\u001b[F\u001b[F\u001b[F\u001b[F\u001b[F\u001b[F\u001b[F\u001b[33;43m_\u001b[30mRUNS \u001b[39;49m tests/\u001b[1mlogin.kt\u001b[22m          \u001b[K\r\n\u001b[33;43m_\u001b[30mRUNS \u001b[39;49m tests/\u001b[1msignup.kt\u001b[22m         \u001b[K\r\n\u001b[33;43m_\u001b[30mRUNS \u001b[39;49m tests/\u001b[1mforgot-password.kt\u001b[22m\u001b[K\r\n\u001b[33;43m_\u001b[30mRUNS \u001b[39;49m tests/\u001b[1mreset-password.kt\u001b[22m \u001b[K\r\n                               \u001b[K\r\nTests: 4 total                 \u001b[K\r\nTime:  1s                      \u001b[K\r\n"]
```

Once processed with this tool, the outputs which occur close together have been merged. The
commands are now more reflective of the writes from the original program.

Additionally, SVGs produced by [svg-term](https://github.com/marionebl/svg-term-cli) on the JSON
output of this tool are significantly smaller than those on the raw JSON.


## Usage

```
$ asciinema rec raw.json
$ asciinema-vsync raw.json vsync.json

$ svg-term --in vsync.json --out demo.svg  # Just an example
```

See `src/test/fixtures/` for example input and expected outputs.

## Install

**Mac OS**

```
$ brew install JakeWharton/repo/asciinema-vsync
```

**Other**

Download standalone JAR from
[latest release](https://github.com/JakeWharton/asciinema-vsync/releases/latest).
On MacOS and Linux you can `chmod +x` and execute the `.jar` directly.
On Windows use `java -jar`.


## License

    Copyright 2020 Jake Wharton

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
