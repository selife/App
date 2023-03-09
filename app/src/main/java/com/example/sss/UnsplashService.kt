package com.example.sss

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UnsplashService {
    @GET("photos")
    suspend fun getPhotos(
        @Header("Authorization") clientId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<Photo>
}