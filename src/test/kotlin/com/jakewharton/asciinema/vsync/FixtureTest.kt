package com.jakewharton.asciinema.vsync

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import java.io.File

@RunWith(Parameterized::class)
class FixtureTest(private val fixtureDir: File) {
	@Test fun run() {
		val input = fixtureDir.resolve("input.json").readText()
		val expected = fixtureDir.resolve("output.json").readText()
		val actual = asciinemaNormalize(input, threshold)
		assertThat(actual).isEqualTo(expected)
	}

	companion object {
		@JvmStatic
		@Parameters(name = "{0}")
		fun params() = File("src/test/fixtures")
			.listFiles { file -> file.isDirectory }
	}
}
