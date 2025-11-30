package com.example.themixologist.core.di

import com.example.themixologist.data.remote.CocktailApi
import com.example.themixologist.data.repository.CocktailRepository
import com.example.themixologist.data.repository.CocktailRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCocktailApi(retrofit: Retrofit): CocktailApi {
        return retrofit.create(CocktailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCocktailRepository(api: CocktailApi): CocktailRepository {
        return CocktailRepositoryImpl(api)
    }
}