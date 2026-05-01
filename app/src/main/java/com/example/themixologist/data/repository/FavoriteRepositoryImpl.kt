package com.example.themixologist.data.repository

import com.example.themixologist.data.local.FavoriteCocktailDao
import com.example.themixologist.data.local.FavoriteCocktailEntity
import com.example.themixologist.domain.model.Cocktail
import com.example.themixologist.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteCocktailDao
) : FavoriteRepository {

    override fun observeFavorites(): Flow<List<Cocktail>> {
        return dao.observeFavorites().map { entities ->
            entities.map { entity ->
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
    }

    override fun observeIsFavorite(id: String): Flow<Boolean> {
        return dao.observeIsFavorite(id)
    }

    override suspend fun toggleFavorite(cocktail: Cocktail, isFavorite: Boolean) {
        val entity = FavoriteCocktailEntity(
            id = cocktail.id,
            name = cocktail.name,
            imageUrl = cocktail.imageUrl,
            category = cocktail.category
        )
        
        if (isFavorite) {
            dao.deleteFavorite(entity)
        } else {
            dao.insertFavorite(entity)
        }
    }
}
