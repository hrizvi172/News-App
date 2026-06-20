package com.hrizvi.multimodulenewsapp.data.remote.dto

import com.hrizvi.multimodulenewsapp.data.local.NewsArticleEntity

// Converts a DTO from the API to a database entity
fun ArticleDto.toNewsArticleEntity(): NewsArticleEntity {
    return NewsArticleEntity(
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content,
        sourceName = this.source?.name,
        author = this.author,
        isFavorite = false
    )
}