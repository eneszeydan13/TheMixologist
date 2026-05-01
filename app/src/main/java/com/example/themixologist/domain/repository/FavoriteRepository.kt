package com.example.themixologist.domain.repository

import com.example.themixologist.domain.model.Cocktail
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun observeFavorites(): Flow<List<Cocktail>>
    fun observeIsFavorite(id: String): Flow<Boolean>
    suspend fun toggleFavorite(cocktail: Cocktail, isFavorite: Boolean)
}
