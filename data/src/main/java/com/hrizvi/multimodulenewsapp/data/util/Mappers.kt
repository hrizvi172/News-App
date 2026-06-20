package com.hrizvi.multimodulenewsapp.data.util

import com.hrizvi.multimodulenewsapp.data.remote.dto.ArticleDto
import com.hrizvi.multimodulenewsapp.domain.model.NewsArticle

fun ArticleDto.toNewsArticle(): NewsArticle{
    return NewsArticle(
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