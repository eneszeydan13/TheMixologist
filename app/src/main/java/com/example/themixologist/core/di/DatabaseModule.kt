package com.example.themixologist.core.di

import android.app.Application
import androidx.room.Room
import com.example.themixologist.data.local.FavoriteCocktailDao
import com.example.themixologist.data.local.MixologistDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMixologistDatabase(app: Application): MixologistDatabase {
        return Room.databaseBuilder(
            app,
            MixologistDatabase::class.java,
            "mixologist_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteCocktailDao(db: MixologistDatabase): FavoriteCocktailDao {
        return db.dao
    }
}
