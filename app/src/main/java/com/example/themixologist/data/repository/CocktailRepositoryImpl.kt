package com.example.themixologist.data.repository

import com.example.themixologist.core.util.Resource
import com.example.themixologist.data.mapper.toDomain
import com.example.themixologist.data.remote.CocktailApi
import com.example.themixologist.domain.model.Cocktail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CocktailRepositoryImpl @Inject constructor(
    private val api: CocktailApi
) : CocktailRepository {

    override fun getCocktails(query: String): Flow<Resource<List<Cocktail>>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = api.searchCocktails(query)

            val cleanList = response.drinks?.map { dto ->
                dto.toDomain()
            } ?: emptyList()

            emit(Resource.Success(cleanList))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))

        }
    }

}