package com.hrizvi.multimodulenewsapp.presentation.favorite_screen

import com.hrizvi.multimodulenewsapp.domain.model.NewsArticle

data class FavoriteScreenState(
    val articles: List<NewsArticle> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)