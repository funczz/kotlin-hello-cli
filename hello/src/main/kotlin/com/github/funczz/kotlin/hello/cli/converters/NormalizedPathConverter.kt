package com.github.funczz.kotlin.hello.cli.converters

import com.beust.jcommander.ParameterException
import com.beust.jcommander.converters.BaseConverter
import java.nio.file.InvalidPathException
import java.nio.file.Path
import java.nio.file.Paths


class NormalizedPathConverter(optionName: String) : BaseConverter<Path>(optionName) {
    override fun convert(value: String): Path {
        try {
            if (value.isBlank()) throw InvalidPathException(value, "Invalid file path")
            return Paths.get(value).normalize()
        } catch (_: InvalidPathException) {
            val encoded = escapeUnprintable(value).toString()
            throw ParameterException(getErrorString(encoded, "a path"))
        }
    }

    companion object {
        private fun escapeUnprintable(value: String): CharSequence {
            val bldr = StringBuilder()
            for (c in value.toCharArray()) {
                if (c < ' ') {
                    bldr.append("\\u").append(String.format("%04X", c.code))
                } else {
                    bldr.append(c)
                }
            }
            return bldr
        }
    }
}
