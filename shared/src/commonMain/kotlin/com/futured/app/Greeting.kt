package com.futured.app

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}
