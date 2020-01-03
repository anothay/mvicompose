package com.rumatu.api.service.mappers

import com.rumatu.api.api.models.CommentsJson
import com.rumatu.api.api.models.PostsJson
import com.rumatu.api.api.models.UsersJson
import com.rumatu.api.service.models.Comment
import com.rumatu.api.service.models.Post
import com.rumatu.api.service.models.User

class ServiceMappers() {

    internal fun mapPosts(posts: List<PostsJson>) = posts.map {
        Post(
            title = it.title,
            body = it.body,
            userId = it.userId,
            id = it.id
        )
    }

    internal fun mapUsers(users: List<UsersJson>) = users.map {
        User(
            userName = it.username,
            id = it.id
        )
    }

    internal fun mapComments(comments: List<CommentsJson>) = comments.map {
        Comment(
            id = it.id,
            postId = it.postId,
            email = it.email,
            name = it.name,
            body = it.body
        )
    }
}
