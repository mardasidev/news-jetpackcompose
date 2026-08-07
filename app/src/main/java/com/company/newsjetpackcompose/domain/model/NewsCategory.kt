package com.company.newsjetpackcompose.domain.model

enum class NewsCategory(
    val apiValue: String,
    val displayName: String
) {
    General("general", "General"),
    Business("business", "Business"),
    Entertainment("entertainment", "Entertainment"),
    Health("health", "Health"),
    Science("science", "Science"),
    Sports("sports", "Sports"),
    Technology("technology", "Technology")
}
