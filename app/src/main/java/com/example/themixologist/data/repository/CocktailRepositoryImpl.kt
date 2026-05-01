package com.example.themixologist.data.repository

import com.example.themixologist.core.util.Resource
import com.example.themixologist.data.mapper.toDomain
import com.example.themixologist.data.remote.CocktailApi
import com.example.themixologist.domain.model.Cocktail
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CocktailRepositoryImpl @Inject constructor(
    private val api: CocktailApi
) : CocktailRepository {

    override fun getCocktails(query: String): Flow<Resource<List<Cocktail>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.searchCocktails(query)

            val cleanList = response.drinks?.map { dto ->
                dto.toDomain()
            } ?: emptyList()

            emit(Resource.Success(cleanList))
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    override fun getCocktailDetails(id: String): Flow<Resource<Cocktail>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.lookupCocktailById(id)
            val dto = response.drinks.firstOrNull() // Get the first result

            if (dto != null) {
                emit(Resource.Success(dto.toDomain()))
            } else {
                emit(Resource.Error("Cocktail not found"))
            }
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }

}