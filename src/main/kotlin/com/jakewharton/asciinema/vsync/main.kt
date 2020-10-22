@file:JvmName("-Main") // Hide from Java.

package com.jakewharton.asciinema.vsync

import okio.buffer
import okio.sink
import okio.source
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.system.exitProcess

internal const val threshold = 0.025f

// Internal to hide from Kotlin.
internal fun main(vararg args: String) {
	val help = "-h" in args || "--help" in args
	if (help || args.size > 2) {
		System.err.println("Usage: asciinema-vsync input.json output.json")
		System.err.println()
		System.err.println("If no output file is specified, stdout will be used. If no")
		System.err.println("input file is specified, stdin will be used.")
		if (!help) {
			exitProcess(1)
		}
		return
	}

	val inStream = args.getOrNull(0)?.let(::FileInputStream) ?: System.`in`
	val outStream = args.getOrNull(1)?.let(::FileOutputStream) ?: System.out

	val input = inStream.source().buffer().use { it.readUtf8() }
	val output = asciinemaNormalize(input, rate = threshold)
	outStream.sink().buffer().use { it.writeUtf8(output) }
}
