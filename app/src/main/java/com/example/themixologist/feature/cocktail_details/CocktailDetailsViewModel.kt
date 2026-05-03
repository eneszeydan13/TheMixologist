package com.example.themixologist.feature.cocktail_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.themixologist.core.base.BaseViewModel
import com.example.themixologist.core.util.Resource
import com.example.themixologist.data.repository.CocktailRepository
import com.example.themixologist.domain.repository.FavoriteRepository
import com.example.themixologist.domain.model.Cocktail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailDetailsViewModel @Inject constructor(
    private val repository: CocktailRepository,
    private val favoriteRepository: FavoriteRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    
    data class UiState(
        val isLoading: Boolean = false,
        val cocktail: Cocktail? = null,
        val isFavorite: Boolean = false
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    private var currentCocktailId: String? = null

    init {
        savedStateHandle.get<String>("cocktailId")?.let { id ->
            currentCocktailId = id
            getDetails(id)
            observeFavoriteStatus(id)
        }
    }

    private fun observeFavoriteStatus(id: String) {
        viewModelScope.launch {
            favoriteRepository.observeIsFavorite(id).collect { isFav ->
                _state.value = _state.value.copy(isFavorite = isFav)
            }
        }
    }

    private fun getDetails(id: String) {
        viewModelScope.launch {
            repository.getCocktailDetails(id).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.value = _state.value.copy(isLoading = true)
                    is Resource.Success -> _state.value =
                        _state.value.copy(isLoading = false, cocktail = result.data)
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false)
                        sendEvent(UiEvent.ShowSnackbar(result.message ?: "Unknown error"))
                    }
                }
            }
        }
    }

    fun toggleFavorite() {
        val cocktail = _state.value.cocktail ?: return
        val isFav = _state.value.isFavorite
        
        viewModelScope.launch {
            favoriteRepository.toggleFavorite(cocktail, isFav)
        }
    }
}