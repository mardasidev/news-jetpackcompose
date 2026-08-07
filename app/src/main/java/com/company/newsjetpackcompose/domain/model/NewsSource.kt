package com.company.newsjetpackcompose.domain.model

data class NewsSource(
    val id: String,
    val name: String,
    val description: String?,
    val category: String?,
    val country: String?,
    val language: String?
)
