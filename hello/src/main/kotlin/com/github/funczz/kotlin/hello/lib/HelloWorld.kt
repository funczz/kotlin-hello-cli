package com.github.funczz.kotlin.hello.lib


object HelloWorld {

    fun getMessage(name: String): String = "Hello world, %s.".format(name)

}
