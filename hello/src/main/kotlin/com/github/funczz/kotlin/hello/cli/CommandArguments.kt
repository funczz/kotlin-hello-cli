package com.github.funczz.kotlin.hello.cli

import com.beust.jcommander.JCommander
import com.beust.jcommander.Parameter
import com.github.funczz.kotlin.hello.cli.converters.NormalizedPathConverter
import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths


class CommandArguments(
    private val args: Array<String>
) {

    /**
     * ヘルプ
     */
    @Parameter(names = ["-h", "--help"], help = true, description = "ヘルプを表示")
    var help = false

    /**
     * バージョン
     */
    @Parameter(names = ["-v", "--version"], help = true, description = "バージョンを表示")
    var version = false

    /**
     * URLを指定する
     */
    @Parameter(
        names = ["-u", "--url"],
        description = "URL",
        required = true,
    )
    lateinit var url: URL


    /**
     * ディレクトリを指定する
     */
    @Parameter(
        names = ["-d", "--directory"],
        description = "ディレクトリのパス",
        defaultValueDescription = "カレントディレクトリ",
        converter = NormalizedPathConverter::class
    )
    var path: Path = Paths.get(".").normalize()

    /**
     * インターバル時間を秒単位で指定する
     */
    @Parameter(
        names = ["-i", "--interval"],
        description = "インターバル時間(秒)",
        defaultValueDescription = "5",
    )
    var intervalSeconds: Int = 5

    /**
     * 再帰処理する場合に指定する
     */
    @Parameter(
        names = ["-r", "--recursive"],
        description = "再帰処理",
    )
    var recursive: Boolean = false

    private val jCommander: JCommander

    /**
     * 初期化
     */
    init {
        validateArgs()

        jCommander = JCommander.newBuilder()
            .addObject(this)
            .programName(CommandConfigure.getProgramName())
            .build()
        jCommander.parse(*args)
    }

    /**
     * 引数を評価する
     */
    fun evaluate(out: StringBuilder = StringBuilder()): StringBuilder {
        out.clear()
        when {
            help -> usage(out)
            version -> version(out)
            else -> {}
        }
        return out
    }

    /**
     * ヘルプを出力
     */
    fun usage(out: StringBuilder = StringBuilder()): StringBuilder {
        jCommander.usage(out)
        return out
    }


    /**
     * バージョンを出力
     */
    fun version(out: StringBuilder = StringBuilder()): StringBuilder {
        out.append(CommandConfigure.getVersion())
        return out
    }

    /**
     * 引数の検証
     */
    private fun validateArgs() {
        if (args.isEmpty()) {
            throw IllegalArgumentException()
        }
        for (arg in args) {
            if (arg.contains("|") || arg.contains("&") ||
                arg.contains(";") || arg.contains("`") ||
                arg.contains("$") || arg.contains("(")
            ) {
                throw SecurityException("不正な文字が含まれています")
            }
        }
    }

}
