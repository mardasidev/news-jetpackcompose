package com.company.newsjetpackcompose.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.newsjetpackcompose.domain.model.NewsCategory
import com.company.newsjetpackcompose.domain.model.NewsSource
import com.company.newsjetpackcompose.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private var headlinesJob: Job? = null
    private var sourcesJob: Job? = null

    /*init {
        refresh()
    }*/

    fun onCategorySelected(category: NewsCategory) {
        if (category == _uiState.value.selectedCategory) return

        _uiState.update {
            it.copy(
                selectedCategory = category,
                selectedSource = null
            )
        }

        loadSources(category)
        loadNews()
    }

    fun onSourceSelected(source: NewsSource) {
        if (source.id == _uiState.value.selectedSource?.id) return

        _uiState.update {
            it.copy(selectedSource = source)
        }

        loadNews()
    }

    fun clearSourceSelection() {
        if (_uiState.value.selectedSource == null) return

        _uiState.update {
            it.copy(selectedSource = null)
        }

        loadNews()
    }

    fun refresh() {
        val category = _uiState.value.selectedCategory
        loadSources(category)
        loadNews()
    }

    private fun loadNews() {
        headlinesJob?.cancel()

        val state = _uiState.value
        headlinesJob = viewModelScope.launch {
            newsRepository.getHeadlines(
                category = if (state.selectedSource == null) state.selectedCategory else null,
                sourceId = state.selectedSource?.id
            ).collect { result ->
                _uiState.update {
                    it.copy(articlesState = result)
                }
            }
        }
    }

    private fun loadSources(category: NewsCategory) {
        sourcesJob?.cancel()

        sourcesJob = viewModelScope.launch {
            newsRepository.getSources(category)
                .collect { result ->
                    _uiState.update {
                        it.copy(sourcesState = result)
                    }
                }
        }
    }
}
