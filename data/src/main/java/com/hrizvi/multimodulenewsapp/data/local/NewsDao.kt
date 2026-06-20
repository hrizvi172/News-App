package com.hrizvi.multimodulenewsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<NewsArticleEntity>)

    @Query("SELECT * FROM news_articles ORDER BY publishedAt DESC")
    fun getNewsArticles(): Flow<List<NewsArticleEntity>>

    @Query("SELECT * FROM news_articles WHERE isFavorite = 1 ORDER BY publishedAt DESC")
    fun getFavoriteArticles(): Flow<List<NewsArticleEntity>>

    @Update
    suspend fun updateArticle(article: NewsArticleEntity)

    @Query("SELECT * FROM news_articles WHERE url = :articleUrl LIMIT 1")
    suspend fun getArticleByUrl(articleUrl: String): NewsArticleEntity?
}