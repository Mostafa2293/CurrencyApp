package com.mostafa.currencyapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform