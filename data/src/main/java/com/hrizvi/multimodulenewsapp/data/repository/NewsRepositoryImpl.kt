package com.hrizvi.multimodulenewsapp.data.repository

import com.hrizvi.multimodulenewsapp.data.BuildConfig
import com.hrizvi.multimodulenewsapp.data.local.NewsDao
import com.hrizvi.multimodulenewsapp.data.local.toNewsArticle
import com.hrizvi.multimodulenewsapp.data.remote.NewsApiService
import com.hrizvi.multimodulenewsapp.data.remote.dto.toNewsArticleEntity
import com.hrizvi.multimodulenewsapp.data.util.Constants.API_KEY
import com.hrizvi.multimodulenewsapp.domain.model.NewsArticle
import com.hrizvi.multimodulenewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val newsDao: NewsDao
) : NewsRepository {

    override fun getNewsHeadlines(country: String): Flow<List<NewsArticle>> {
        return newsDao.getNewsArticles().map { entities ->
            entities.map { it.toNewsArticle() }
        }
    }

    override suspend fun refreshNewsHeadlines(country: String) {
        try {
            val response = newsApiService.getNewsHeadlines(country, API_KEY)
            val newsEntities = response.articles.map { it.toNewsArticleEntity() }
            newsDao.insertAll(newsEntities)
        } catch (e: Exception) {
            println("Network error: ${e.message}")
        }
    }

    override fun getFavoriteArticles(): Flow<List<NewsArticle>> {
        return newsDao.getFavoriteArticles().map { entities ->
            entities.map { it.toNewsArticle() }
        }
    }

    override suspend fun deleteArticleFromFavorites(article: NewsArticle) {
        val articleEntity = newsDao.getArticleByUrl(article.url)
        if (articleEntity != null) {
            val updatedEntity = articleEntity.copy(isFavorite = false)
            newsDao.updateArticle(updatedEntity)
        }
    }

    override suspend fun toggleFavoriteStatus(article: NewsArticle) {
        val entity = newsDao.getArticleByUrl(article.url)
        if (entity != null){
            val updatedEntity = entity.copy(isFavorite = !entity.isFavorite)
            newsDao.updateArticle(updatedEntity)
        }
    }
}