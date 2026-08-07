package com.company.newsjetpackcompose.presentation.news

import com.company.newsjetpackcompose.core.ResultState
import com.company.newsjetpackcompose.domain.model.Article
import com.company.newsjetpackcompose.domain.model.NewsCategory
import com.company.newsjetpackcompose.domain.model.NewsSource

data class NewsUiState(
    val articlesState: ResultState<List<Article>> = ResultState.Loading,
    val sourcesState: ResultState<List<NewsSource>> = ResultState.Loading,
    val categories: List<NewsCategory> = NewsCategory.entries,
    val selectedCategory: NewsCategory = NewsCategory.General,
    val selectedSource: NewsSource? = null
)
