package com.hrizvi.multimodulenewsapp.presentation.news_screen

import com.hrizvi.multimodulenewsapp.domain.model.NewsArticle

data class NewsScreenState(
    val newsArticles: List<NewsArticle> = emptyList(),
    val filteredArticles: List<NewsArticle> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val searchQuery: String = "",
    val isSearching: Boolean = false
)