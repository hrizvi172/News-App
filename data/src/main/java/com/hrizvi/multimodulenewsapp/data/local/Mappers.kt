package com.hrizvi.multimodulenewsapp.data.local

import com.hrizvi.multimodulenewsapp.domain.model.NewsArticle

// Converts a database entity to a domain model
fun NewsArticleEntity.toNewsArticle(): NewsArticle {
    return NewsArticle(
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content,
        sourceName = this.sourceName,
        author = this.author,
        isFavorite = this.isFavorite
    )
}