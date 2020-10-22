#!/usr/bin/env bash

if ! command -v svg-term &> /dev/null; then
    echo "Command 'svg-term' not found. Please install with 'npm install -g svg-term-cli'."
    exit
fi

set -e

REPO_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

"$REPO_DIR/gradlew" -q --console plain -p "$REPO_DIR" assemble

for fixture in $REPO_DIR/src/test/fixtures/*/; do
	name=$(basename "$fixture")
	echo "Updating $name..."
	"$REPO_DIR/build/asciinema-vsync.jar" "$fixture/input.json" "$fixture/output.json"
done

echo "Done"
