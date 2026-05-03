package com.example.themixologist.feature.cocktail_list

import com.example.themixologist.core.base.BaseViewModel
import androidx.lifecycle.viewModelScope
import com.example.themixologist.core.util.Resource
import com.example.themixologist.data.repository.CocktailRepository
import com.example.themixologist.domain.model.Cocktail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailListViewModel @Inject constructor(
    private val repository: CocktailRepository
) : BaseViewModel() {

    // UI State Data Class
    data class UiState(
        val isLoading: Boolean = false,
        val cocktails: List<Cocktail> = emptyList(),
        val error: String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    private val _searchQuery = MutableStateFlow("a")

    init {
        observeSearch()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeSearch() {
        viewModelScope.launch {
            _searchQuery
                .debounce(300L) // Wait 300ms after the user stops typing
                .distinctUntilChanged() // Don't search if the query is exactly the same
                .flatMapLatest { query ->
                    // flatMapLatest cancels the previous flow (network request) 
                    // if a new query is emitted before it finishes!
                    repository.getCocktails(query)
                }
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true, error = null)
                        }

                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                cocktails = result.data ?: emptyList(),
                                error = null
                            )
                        }

                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                error = result.message
                            )
                            sendEvent(UiEvent.ShowSnackbar(result.message ?: "Something went wrong!"))
                        }
                    }
                }
        }
    }

    fun search(query: String) {
        if (query.isNotBlank()) {
            _searchQuery.value = query
        }
    }
}