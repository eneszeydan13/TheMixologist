package com.example.themixologist.feature.favorites

import androidx.lifecycle.viewModelScope
import com.example.themixologist.core.base.BaseViewModel
import com.example.themixologist.domain.model.Cocktail
import com.example.themixologist.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoriteRepository
) : BaseViewModel() {

    val favoriteCocktails: StateFlow<List<Cocktail>> = repository.observeFavorites()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
