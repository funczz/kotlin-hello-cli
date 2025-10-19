package com.github.funczz.kotlin.hello.cli

import java.io.PrintStream

interface CommandMessage {

    fun StringBuilder.appendIfNotBlank(str: String?) {
        if (str == null) return
        if (str.isBlank()) return
        this.append(str)
    }

    fun StringBuilder.newLine() {
        this.append("\n")
    }

    fun StringBuilder.printlnIfIsNotBlank(ps: PrintStream, fn: () -> Unit = {}) {
        if (this.isNotBlank()) {
            ps.println(this)
            this.clear()
            fn()
        }
    }


}
