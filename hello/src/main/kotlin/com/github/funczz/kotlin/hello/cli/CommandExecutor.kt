package com.github.funczz.kotlin.hello.cli

import com.github.funczz.kotlin.hello.lib.HelloWorld
import java.io.InputStream
import java.io.PrintStream


object CommandExecutor : CommandMessage {

    fun execute(
        args: CommandArguments,
        stdIn: InputStream,
        stdOut: PrintStream,
        stdErr: PrintStream,
    ): CommandStatus {
        val stdOutMessage = StringBuilder()
        val stdErrMessage = StringBuilder()

        stdOutMessage.append("Please enter your name:")
        stdOutMessage.printlnIfIsNotBlank(ps = stdOut)
        val reader = stdIn.bufferedReader(Charsets.UTF_8)
        val name = reader.readLine() ?: ""

        stdErrMessage.append("[args]       URL: %s".format(args.url.toString()))
        stdErrMessage.newLine()
        stdErrMessage.append("[args] Directory: %s".format(args.path.toFile().canonicalPath))
        stdErrMessage.newLine()
        stdErrMessage.append("[args]  Interval: %d".format(args.intervalSeconds))
        stdErrMessage.newLine()
        stdErrMessage.append("[args] Recursive: %s".format(args.recursive.toString()))
        stdErrMessage.newLine()
        stdErrMessage.append("[stdIn] YourName: %s".format(name))
        stdErrMessage.printlnIfIsNotBlank(ps = stdErr)

        val message = HelloWorld.getMessage(name = name)
        stdOutMessage.appendIfNotBlank(message)
        stdOutMessage.printlnIfIsNotBlank(ps = stdOut)

        return when (
            name.isNotBlank()
        ) {
            true -> CommandStatus.OK
            else -> CommandStatus.MINOR_PROBLEMS
        }

    }

}
