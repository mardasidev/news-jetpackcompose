package com.company.newsjetpackcompose.data.mapper

import com.company.newsjetpackcompose.data.remote.dto.ArticleDto
import com.company.newsjetpackcompose.data.remote.dto.SourceDto
import com.company.newsjetpackcompose.domain.model.Article
import com.company.newsjetpackcompose.domain.model.NewsSource

fun ArticleDto.toDomain(): Article = Article(
    title = title.orEmpty(),
    description = description,
    url = url.orEmpty(),
    imageUrl = urlToImage,
    publishedAt = publishedAt,
    author = author,
    sourceName = source?.name.orEmpty()
)

fun SourceDto.toDomain(): NewsSource = NewsSource(
    id = id.orEmpty(),
    name = name.orEmpty(),
    description = description,
    category = category,
    country = country,
    language = language
)
