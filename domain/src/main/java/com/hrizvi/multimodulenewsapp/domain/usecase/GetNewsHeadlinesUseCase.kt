package com.hrizvi.multimodulenewsapp.domain.usecase

import com.hrizvi.multimodulenewsapp.domain.model.NewsArticle
import com.hrizvi.multimodulenewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNewsHeadlinesUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(country: String): Flow<List<NewsArticle>> {
        return repository.getNewsHeadlines(country)
    }
}