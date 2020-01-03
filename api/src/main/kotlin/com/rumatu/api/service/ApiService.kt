package com.rumatu.api.service

import com.rumatu.api.api.ApiEndpoints
import com.rumatu.api.service.mappers.ServiceMappers
import com.rumatu.api.service.models.Comment
import com.rumatu.api.service.models.Post
import com.rumatu.api.service.models.User
import io.reactivex.Single

class ApiService internal constructor(
    private val apiEndpoints: ApiEndpoints,
    private val serviceMappers: ServiceMappers
) {

    fun getComments(): Single<List<Comment>> {
        return apiEndpoints.getComments().map { serviceMappers.mapComments(it) }
    }

    fun getUsers(): Single<List<User>> {
        return apiEndpoints.getUsers().map { serviceMappers.mapUsers(it) }
    }

    fun getPosts(): Single<List<Post>> {
        return apiEndpoints.getPosts().map { serviceMappers.mapPosts(it) }
    }
}
