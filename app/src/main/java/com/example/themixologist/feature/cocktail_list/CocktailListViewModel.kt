package com.example.themixologist.feature.cocktail_list

import com.example.themixologist.core.base.BaseViewModel
import androidx.lifecycle.viewModelScope
import com.example.themixologist.core.util.Resource
import com.example.themixologist.data.repository.CocktailRepository
import com.example.themixologist.domain.model.Cocktail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    init {
        // Initial search
        search("a")
    }

    fun search(query: String) {
        // Launch a coroutine in the ViewModel scope
        viewModelScope.launch {
            repository.getCocktails(query).collect { result ->
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
                        // Also trigger a one-time event for a Snackbar
                        sendEvent(UiEvent.ShowSnackbar(result.message ?: "Something went wrong!"))
                    }
                }
            }
        }
    }
}