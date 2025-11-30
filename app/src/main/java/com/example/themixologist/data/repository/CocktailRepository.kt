package com.example.themixologist.data.repository

import com.example.themixologist.core.util.Resource
import com.example.themixologist.domain.model.Cocktail
import kotlinx.coroutines.flow.Flow

interface CocktailRepository {
    fun getCocktails(query: String): Flow<Resource<List<Cocktail>>>
}