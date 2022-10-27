package com.zibran.githubapp.builder

import com.zibran.githubapp.services.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

    private const val BASE_URL = "https://api.github.com/"

    private fun getRetrofit(): Retrofit {
        // returning a retrofit object
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

}