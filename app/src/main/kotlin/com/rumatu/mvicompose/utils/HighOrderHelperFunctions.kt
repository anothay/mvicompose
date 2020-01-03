package com.rumatu.mvicompose.utils

fun consume(func: () -> Unit): Boolean {
    func()
    return true
}
