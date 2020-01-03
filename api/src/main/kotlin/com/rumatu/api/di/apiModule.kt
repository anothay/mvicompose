package com.rumatu.api.di

import com.rumatu.api.api.ApiEndpoints
import com.rumatu.api.service.ApiService
import com.rumatu.api.service.mappers.ServiceMappers
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    single { get<Retrofit>().create(ApiEndpoints::class.java) }

    single { ApiService(get(), get()) }

    single { ServiceMappers() }
}
