package com.hrizvi.multimodulenewsapp.data.remote

import com.hrizvi.multimodulenewsapp.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query
interface NewsApiService{
    @GET("top-headlines")
    suspend fun getNewsHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): NewsResponseDto

}