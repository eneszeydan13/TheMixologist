package com.example.themixologist.feature.favorites

import androidx.lifecycle.viewModelScope
import com.example.themixologist.core.base.BaseViewModel
import com.example.themixologist.data.local.FavoriteCocktailDao
import com.example.themixologist.domain.model.Cocktail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val dao: FavoriteCocktailDao
) : BaseViewModel() {

    val favoriteCocktails: StateFlow<List<Cocktail>> = dao.observeFavorites()
        .map { list -> 
            list.map { entity -> 
                Cocktail(
                    id = entity.id,
                    name = entity.name,
                    imageUrl = entity.imageUrl,
                    category = entity.category,
                    alcoholic = "",
                    glass = "",
                    instructions = emptyList(),
                    ingredients = emptyList()
                )
            } 
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
