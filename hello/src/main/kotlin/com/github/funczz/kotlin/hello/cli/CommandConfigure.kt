package com.github.funczz.kotlin.hello.cli


object CommandConfigure {

    const val PROGRAM_NAME = "hello"

    const val MAJOR_VERSION = 0

    const val MINOR_VERSION = 1

    fun getProgramName(): String = PROGRAM_NAME

    fun getVersion(): String = "%d.%d".format(MAJOR_VERSION, MINOR_VERSION)
}
