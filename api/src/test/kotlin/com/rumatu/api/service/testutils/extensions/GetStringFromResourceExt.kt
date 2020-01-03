package com.rumatu.api.service.testutils.extensions

fun Any.getStringFromResource(filePath: String): String =
    this::class.java.getResource("/$filePath").readText()
