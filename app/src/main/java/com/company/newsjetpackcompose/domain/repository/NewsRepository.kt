package com.company.newsjetpackcompose.domain.repository

import com.company.newsjetpackcompose.core.ResultState
import com.company.newsjetpackcompose.domain.model.Article
import com.company.newsjetpackcompose.domain.model.NewsCategory
import com.company.newsjetpackcompose.domain.model.NewsSource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getHeadlines(
        category: NewsCategory?,
        sourceId: String?
    ): Flow<ResultState<List<Article>>>

    fun getSources(
        category: NewsCategory?
    ): Flow<ResultState<List<NewsSource>>>
}
