package com.hrizvi.multimodulenewsapp.domain.usecase

import com.hrizvi.multimodulenewsapp.domain.model.NewsArticle

class SearchNewsUseCase {
    operator fun invoke(articles: List<NewsArticle>, query: String): List<NewsArticle> {
        if (query.isBlank()) return articles

        val searchTerms = query.trim().lowercase().split(" ")

        return articles.filter { article ->
            searchTerms.any { term ->
                article.title.lowercase().contains(term) ||
                article.description?.lowercase()?.contains(term) == true ||
                article.sourceName?.lowercase()?.contains(term) == true
            }
        }.sortedByDescending { article ->
            when {
                article.title.lowercase().contains(query.lowercase()) -> 3
                article.description?.lowercase()?.contains(query.lowercase()) == true -> 2
                else -> 1
            }
        }
    }
}