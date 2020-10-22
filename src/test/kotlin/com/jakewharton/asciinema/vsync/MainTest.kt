package com.jakewharton.asciinema.vsync

import org.junit.Test

class MainTest {
	@Test fun helpDoesNotExitProcess() {
		// Bare flag.
		main("-h")
		main("--help")

		// Flag with one file.
		main("-h", "old.json")
		main("--help", "old.json")
		main("old.json", "-h")
		main("old.json", "--help")

		// Flag with two files.
		main("-h", "old.json", "new.json")
		main("--help", "old.json", "new.json")
		main("old.json", "-h", "new.json")
		main("old.json", "--help", "new.json")
		main("old.json", "new.json", "-h")
		main("old.json", "new.json", "--help")

		// Flag with too many files.
		main("-h", "old.json", "new.json", "wat.json")
		main("--help", "old.json", "new.json", "wat.json")
		main("old.json", "-h", "new.json", "wat.json")
		main("old.json", "--help", "new.json", "wat.json")
		main("old.json", "new.json", "-h", "wat.json")
		main("old.json", "new.json", "--help", "wat.json")
		main("old.json", "new.json", "wat.json", "-h")
		main("old.json", "new.json", "wat.json", "--help")
	}
}
