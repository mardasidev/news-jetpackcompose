package com.company.newsjetpackcompose.data.repository

import com.company.newsjetpackcompose.core.ResultState
import com.company.newsjetpackcompose.data.remote.ApiService
import com.company.newsjetpackcompose.data.remote.dto.ArticleDto
import com.company.newsjetpackcompose.data.remote.dto.NewsResponseDto
import com.company.newsjetpackcompose.data.remote.dto.SourceDto
import com.company.newsjetpackcompose.domain.model.Article
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NewsRepositoryImplTest {

    private val mockApiService: ApiService = mockk()
    private val repository = NewsRepositoryImpl(mockApiService)


    @Test
    fun getHeadlines() = runTest {
        val mockHeadlines = listOf<ArticleDto>(
            ArticleDto(
                title = "Mock Title",
                description = "Mock Description",
                url = "https://mockurl.com",
                urlToImage = "https://mockimage.com/300x200",
                publishedAt = "2023-09-25T12:00:00Z",
                author = "Mock Author",
                source = SourceDto(
                    id = "mock-source-id",
                    name = "Mock Source",
                    description = "Mock Source Description",
                    url = "https://mocksource.com",
                    category = "mock-category",
                    language = "en",
                    country = "US",
                ),
                content = "mock content"
            ),
            ArticleDto(
                title = "Mock Title2",
                description = "Mock Description2",
                url = "https://mockurl.com",
                urlToImage = "https://mockimage.com/300x200",
                publishedAt = "2023-09-25T12:00:00Z",
                author = "Mock Author2",
                source = SourceDto(
                    id = "mock-source-id",
                    name = "Mock Source2",
                    description = "Mock Source Description2",
                    url = "https://mocksource.com",
                    category = "mock-category",
                    language = "en",
                    country = "US",
                ),
                content = "mock content2"
            ),
        )

        val mockResponse = NewsResponseDto(
            status = "ok",
            totalResults = 2,
            articles = mockHeadlines
        )

        coEvery {
            mockApiService.getTopHeadlines(
                country = any(),
                category = any(),
                sources = any()
            )
        } returns mockResponse

        val result = repository.getHeadlines(null, null).toList();
        val success = result[1] as ResultState.Success
        assertEquals(mockResponse.articles, success.data)
    }

}