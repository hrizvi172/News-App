package com.hrizvi.multimodulenewsapp.domain.model

data class NewsArticle(
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    val sourceName: String?,
    val isFavorite: Boolean = false
)