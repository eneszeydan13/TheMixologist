package com.example.themixologist.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCocktailDao {
    @Query("SELECT * FROM favorite_cocktails")
    fun observeFavorites(): Flow<List<FavoriteCocktailEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_cocktails WHERE id = :id)")
    fun observeIsFavorite(id: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(cocktail: FavoriteCocktailEntity)

    @Delete
    suspend fun deleteFavorite(cocktail: FavoriteCocktailEntity)
}
