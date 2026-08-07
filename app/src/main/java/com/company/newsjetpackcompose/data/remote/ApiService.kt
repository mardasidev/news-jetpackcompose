package com.company.newsjetpackcompose.data.remote

import com.company.newsjetpackcompose.core.ApiConfig
import com.company.newsjetpackcompose.data.remote.dto.NewsResponseDto
import com.company.newsjetpackcompose.data.remote.dto.SourcesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String? = null,
        @Query("category") category: String? = null,
        @Query("sources") sources: String? = null,
        @Query("apiKey") apiKey: String = ApiConfig.API_KEY,
        @Query("pageSize") pageSize: Int = ApiConfig.DEFAULT_PAGE_SIZE
    ): NewsResponseDto

    @GET("v2/top-headlines/sources")
    suspend fun getSources(
        @Query("category") category: String? = null,
        @Query("language") language: String? = ApiConfig.DEFAULT_LANGUAGE,
        @Query("country") country: String? = ApiConfig.DEFAULT_COUNTRY,
        @Query("apiKey") apiKey: String = ApiConfig.API_KEY
    ): SourcesResponseDto
}
