package com.example.themixologist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCocktailEntity::class], version = 1, exportSchema = false)
abstract class MixologistDatabase : RoomDatabase() {
    abstract val dao: FavoriteCocktailDao
}
