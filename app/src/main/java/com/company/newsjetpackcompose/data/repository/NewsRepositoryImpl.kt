package com.company.newsjetpackcompose.data.repository

import com.company.newsjetpackcompose.core.ApiConfig
import com.company.newsjetpackcompose.core.ResultState
import com.company.newsjetpackcompose.data.mapper.toDomain
import com.company.newsjetpackcompose.data.remote.ApiService
import com.company.newsjetpackcompose.domain.model.Article
import com.company.newsjetpackcompose.domain.model.NewsCategory
import com.company.newsjetpackcompose.domain.model.NewsSource
import com.company.newsjetpackcompose.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : NewsRepository {
    override fun getHeadlines(
        category: NewsCategory?,
        sourceId: String?
    ): Flow<ResultState<List<Article>>> = flow {
        emit(ResultState.Loading)

        val response = apiService.getTopHeadlines(
            country = if (sourceId == null) ApiConfig.DEFAULT_COUNTRY else null,
            category = if (sourceId == null) category?.apiValue else null,
            sources = sourceId
        )

        val articles = response.articles
            .orEmpty()
            .map { it.toDomain() }
            .filter { it.title.isNotBlank() && it.url.isNotBlank() }

        emit(
            if (articles.isEmpty()) {
                ResultState.Empty
            } else {
                ResultState.Success(articles)
            }
        )
    }.catch { throwable ->
        emit(ResultState.Error(throwable.message ?: "Something went wrong", throwable))
    }.flowOn(Dispatchers.IO)

    override fun getSources(
        category: NewsCategory?
    ): Flow<ResultState<List<NewsSource>>> = flow {
        emit(ResultState.Loading)

        val sources = apiService.getSources(
            category = category?.apiValue
        ).sources
            .orEmpty()
            .map { it.toDomain() }
            .filter { it.id.isNotBlank() && it.name.isNotBlank() }

        emit(
            if (sources.isEmpty()) {
                ResultState.Empty
            } else {
                ResultState.Success(sources)
            }
        )
    }.catch { throwable ->
        emit(ResultState.Error(throwable.message ?: "Something went wrong", throwable))
    }.flowOn(Dispatchers.IO)
}
