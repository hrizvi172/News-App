package com.hrizvi.multimodulenewsapp.domain.repository

import com.hrizvi.multimodulenewsapp.domain.model.NewsArticle
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsHeadlines(country: String): Flow<List<NewsArticle>>
    suspend fun refreshNewsHeadlines(country: String)

    suspend fun toggleFavoriteStatus(article: NewsArticle)
    fun getFavoriteArticles(): Flow<List<NewsArticle>>
    suspend fun deleteArticleFromFavorites(article: NewsArticle)

}