package com.example.themixologist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cocktails")
data class FavoriteCocktailEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String,
    val category: String
)
