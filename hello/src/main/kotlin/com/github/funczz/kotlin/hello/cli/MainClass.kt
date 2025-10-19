package com.github.funczz.kotlin.hello.cli

import kotlin.system.exitProcess


object MainClass : CommandMessage {

    @JvmStatic
    fun main(args: Array<String>) {
        val stdOutMessage = StringBuilder()
        val stdErrMessage = StringBuilder()
        val commandArguments = try {
            CommandArguments(args = args).also {
                it.evaluate(out = stdOutMessage)
                stdOutMessage.printlnIfIsNotBlank(ps = System.out) {
                    exit(commandStatus = CommandStatus.OK)
                }
            }
        } catch (e: Exception) {
            //output StdErr
            stdErrMessage.appendIfNotBlank(e.message)
            stdErrMessage.printlnIfIsNotBlank(ps = System.err)
            //output StdOut
            stdOutMessage.appendIfNotBlank("try --help for more information.")
            stdOutMessage.printlnIfIsNotBlank(ps = System.out)
            exit(commandStatus = CommandStatus.SERIOUS_TROUBLE)
        }
        val commandStatus = CommandExecutor.execute(
            args = commandArguments,
            stdIn = System.`in`,
            stdOut = System.out,
            stdErr = System.err,
        )
        exit(commandStatus = commandStatus)
    }

    fun exit(commandStatus: CommandStatus): Nothing {
        exitProcess(commandStatus.ordinal)
    }

}
