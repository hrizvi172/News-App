package com.hrizvi.multimodulenewsapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hrizvi.multimodulenewsapp.domain.model.NewsArticle


@Entity(tableName = "news_articles")
data class NewsArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    val sourceName: String?,
    val isFavorite: Boolean = false
){
    fun toNewsArticle(): NewsArticle {
        return NewsArticle(
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content,
            sourceName = sourceName,
            isFavorite = isFavorite
        )
    }

    companion object {
        fun fromNewsArticle(newsArticle: NewsArticle): NewsArticleEntity {
            return NewsArticleEntity(
                author = newsArticle.author,
                title = newsArticle.title,
                description = newsArticle.description,
                url = newsArticle.url,
                urlToImage = newsArticle.urlToImage,
                publishedAt = newsArticle.publishedAt,
                content = newsArticle.content,
                sourceName = newsArticle.sourceName,
                isFavorite = newsArticle.isFavorite
            )
        }
    }
}