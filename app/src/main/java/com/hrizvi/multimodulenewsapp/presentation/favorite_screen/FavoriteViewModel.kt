package com.hrizvi.multimodulenewsapp.presentation.favorite_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrizvi.multimodulenewsapp.domain.model.NewsArticle
import com.hrizvi.multimodulenewsapp.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel(){
    private val _state = MutableStateFlow(FavoriteScreenState())
    val state = _state.asStateFlow()

    init {
        getFavoriteArticles()
    }

    private fun getFavoriteArticles(){
        newsRepository.getFavoriteArticles()
            .onEach { articles ->
                _state.value = state.value.copy(
                    articles = articles,
                    isLoading = false
                )
            }.launchIn(viewModelScope)
    }
    fun onFavoriteClicked(article: NewsArticle) {
        viewModelScope.launch {
            newsRepository.deleteArticleFromFavorites(article)
        }
    }

}