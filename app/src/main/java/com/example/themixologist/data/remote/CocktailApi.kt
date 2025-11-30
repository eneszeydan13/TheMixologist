package com.example.themixologist.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {

    @GET("search.php")
    suspend fun searchCocktails(
        @Query("s") query: String
    ): CocktailResponseDto

    @GET("lookup.php")
    suspend fun lookupCocktailById(
        @Query("i") id: String
    ): CocktailResponseDto

}