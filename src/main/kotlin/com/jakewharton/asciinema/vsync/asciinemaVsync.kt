@file:JvmName("AsciinemaVsync")

package com.jakewharton.asciinema.vsync

import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import okio.Buffer

@JvmName("normalize")
fun asciinemaNormalize(input: String, rate: Float): String {
	val (firstLine, otherLines) = input.split("\n", limit = 2)

	return buildString {
		appendLine(firstLine)

		var previousTimestamp = Double.MIN_VALUE
		var previousOutput = ""

		fun writeOutput() {
			if (previousTimestamp == Double.MIN_VALUE) {
				return
			}
			val buffer = Buffer()
			val writer = JsonWriter.of(buffer)
			writer.beginArray()
			writer.value(previousTimestamp)
			writer.value("o")
			writer.value(previousOutput)
			writer.endArray()
			writer.close()
			appendLine(buffer.readUtf8())
		}

		for (line in otherLines.lines().filter(String::isNotEmpty)) {
			val reader = JsonReader.of(Buffer().writeUtf8(line))
			reader.beginArray()
			val timestamp = reader.nextDouble()
			val command = reader.nextString()
			val output = reader.nextString()
			reader.endArray()

			check(command == "o") { "Unknown command: $command" }

			if (timestamp - previousTimestamp >= rate) {
				writeOutput()
				previousTimestamp = timestamp
				previousOutput = ""
			}
			previousOutput += output
		}
		writeOutput()
	}
}
