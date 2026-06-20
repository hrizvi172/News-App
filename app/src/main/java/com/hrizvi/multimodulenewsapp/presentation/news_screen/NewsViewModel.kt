package com.hrizvi.multimodulenewsapp.presentation.news_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrizvi.multimodulenewsapp.domain.model.NewsArticle
import com.hrizvi.multimodulenewsapp.domain.repository.NewsRepository
import com.hrizvi.multimodulenewsapp.domain.usecase.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val searchNewsUseCase: SearchNewsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NewsScreenState())
    val state = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    private var allArticles: List<NewsArticle> = emptyList()

    init {
        getNewsHeadlines()
        setupSearch()
    }

    private fun getNewsHeadlines() {
        viewModelScope.launch {
            try {
                newsRepository.refreshNewsHeadlines(country = "us")
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to fetch news: ${e.message}"
                )
                return@launch
            }
        }

        newsRepository.getNewsHeadlines(country = "us")
            .catch { exception ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Error loading news: ${exception.message}"
                )
            }
            .onEach { articles ->
                // Remove duplicates based on URL and title
                val uniqueArticles = removeDuplicates(articles)
                allArticles = uniqueArticles

                val filteredArticles = if (_state.value.searchQuery.isBlank()) {
                    uniqueArticles
                } else {
                    searchNewsUseCase(uniqueArticles, _state.value.searchQuery)
                }

                _state.value = _state.value.copy(
                    newsArticles = uniqueArticles,
                    filteredArticles = filteredArticles,
                    isLoading = false,
                    error = null
                )
            }.launchIn(viewModelScope)
    }

    private fun setupSearch() {
        _searchQuery
            .debounce(300)
            .distinctUntilChanged()
            .onEach { query ->
                performSearch(query)
            }
            .launchIn(viewModelScope)
    }

    private fun performSearch(query: String) {
        val filteredArticles = if (query.isBlank()) {
            allArticles
        } else {
            searchNewsUseCase(allArticles, query)
        }

        _state.value = _state.value.copy(
            searchQuery = query,
            isSearching = query.isNotBlank(),
            filteredArticles = filteredArticles
        )
    }

    fun onSearchQueryChanged(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
        _searchQuery.value = query
    }

    fun onClearSearch() {
        _searchQuery.value = ""
    }

    fun refreshNews() {
        _state.value = _state.value.copy(isLoading = true, error = null)
        getNewsHeadlines()
    }

    fun onFavoriteClicked(article: NewsArticle) {
        viewModelScope.launch {
            try {
                newsRepository.toggleFavoriteStatus(article)
                updateArticleInState(article.copy(isFavorite = !article.isFavorite))
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Failed to update favorite: ${e.message}"
                )
            }
        }
    }

    private fun updateArticleInState(updatedArticle: NewsArticle) {
        allArticles = allArticles.map { article ->
            if (article.url == updatedArticle.url) updatedArticle else article
        }

        val filteredArticles = if (_state.value.searchQuery.isBlank()) {
            allArticles
        } else {
            searchNewsUseCase(allArticles, _state.value.searchQuery)
        }

        _state.value = _state.value.copy(
            newsArticles = allArticles,
            filteredArticles = filteredArticles
        )
    }

    private fun removeDuplicates(articles: List<NewsArticle>): List<NewsArticle> {
        val seen = mutableSetOf<String>()
        return articles.filter { article ->
            val key = "${article.url}-${article.title}".lowercase()
            if (key in seen) {
                false
            } else {
                seen.add(key)
                true
            }
        }
    }
}