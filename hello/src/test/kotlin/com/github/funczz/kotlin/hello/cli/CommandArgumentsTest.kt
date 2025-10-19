package com.github.funczz.kotlin.hello.cli

import com.beust.jcommander.ParameterException
import com.github.funczz.kotlin.junit5.Cases
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths

@Suppress("NonAsciiCharacters")
class CommandArgumentsTest : Cases {

    @Test
    fun `引数にhelpオプションが含まれるなら、ヘルプを出力する`() {
        val expected = "Usage: "
        val actual = CommandArguments(arrayOf("--help", "--version")).evaluate()
        assertEquals(expected, actual.toString().substring(0, 7))
    }

    @Test
    fun `引数にversionオプションが含まれるなら、バージョンを出力する`() {
        val expected = CommandConfigure.getVersion()
        val actual = CommandArguments(arrayOf("--version", "--recursive")).evaluate()
        assertEquals(expected, actual.toString())
    }

    @Test
    fun `引数が空なら、IllegalArgumentExceptionをスローする`() {
        val actual = assertThrows<IllegalArgumentException> {
            CommandArguments(emptyArray()).evaluate()
        }
        assertEquals(null, actual.message)
    }

    @Test
    fun `引数に未知のオプションが含まれるなら、ParameterExceptionをスローする`() {
        val expected = "Was passed main parameter '--unknown' but no main parameter was defined in your arg class"
        val actual = assertThrows<ParameterException> {
            CommandArguments(arrayOf("--unknown")).evaluate()
        }
        assertEquals(expected, actual.message)
    }

    @Test
    fun `引数にShell特殊記号が含まれるなら、SecurityExceptionをスローする`() {
        val expected = "不正な文字が含まれています"
        val actual = assertThrows<SecurityException> {
            CommandArguments(arrayOf("-p &path"))
        }
        assertEquals(expected, actual.message)
    }

    @TestFactory
    fun `正常系 URLとその他オプション`() = casesDynamicTest(
        Data(
            desc = "urlを指定する",
            argument = "-u $exampleURL",
            url = exampleURL,
        ),
        Data(
            desc = "urlとintervalを指定する",
            argument = "-u $exampleURL -i 3",
            url = exampleURL,
            intervalSeconds = 3,
        ),
        Data(
            desc = "urlとdirectoryを指定する",
            argument = "-u $exampleURL -d ./bar",
            url = exampleURL,
            path = Paths.get("./bar").normalize(),
        ),
        Data(
            desc = "urlとrecursiveを指定する",
            argument = "-u $exampleURL -r",
            url = exampleURL,
            recursive = true,
        ),
    ) {
        val actual = CommandArguments(it.args)
        assertEquals(it.help, actual.help)
        assertEquals(it.version, actual.version)
        assertEquals(it.url, actual.url)
        assertEquals(it.intervalSeconds, actual.intervalSeconds)
        assertEquals(it.path, actual.path)
        assertEquals(it.recursive, actual.recursive)
        assertEquals(it.url.toString(), actual.url.toString())
        assertEquals(it.path.toString(), actual.path.toString())
    }

    private val exampleURL: URL = URL("https://example.com/foo/")

    private data class Data(
        val desc: String,
        val argument: String,
        val help: Boolean = false,
        val version: Boolean = false,
        val url: URL = URL("https://example.com/foo/"),
        val intervalSeconds: Int = 5,
        val path: Path = Paths.get(".").normalize(),
        val recursive: Boolean = false,
    ) {
        val args: Array<String> = argument.split(" ").toTypedArray()


        override fun toString(): String {
            return desc
        }
    }

}