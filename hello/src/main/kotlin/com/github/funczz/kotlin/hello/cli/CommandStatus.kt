package com.github.funczz.kotlin.hello.cli


enum class CommandStatus {
    OK,
    MINOR_PROBLEMS,  //e.g., cannot access subdirectory
    SERIOUS_TROUBLE, //e.g., cannot access command-line argument
}
