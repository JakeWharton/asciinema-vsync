#!/usr/bin/env bash

if ! command -v svg-term &> /dev/null; then
    echo "Command 'svg-term' not found. Please install with 'npm install -g svg-term-cli'."
    exit
fi

REPO_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

set -ex

svg-term --in "$REPO_DIR/src/test/fixtures/jest/input.json" --out="$REPO_DIR/src/test/fixtures/jest/input.svg" --width=36 --height=14 --no-cursor
svg-term --in "$REPO_DIR/src/test/fixtures/jest/output.json" --out="$REPO_DIR/src/test/fixtures/jest/output.svg" --width=36 --height=14 --no-cursor
