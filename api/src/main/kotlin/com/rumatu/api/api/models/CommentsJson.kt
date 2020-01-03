package com.rumatu.api.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentsJson(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)
