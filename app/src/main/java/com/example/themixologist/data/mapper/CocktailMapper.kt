package com.example.themixologist.data.mapper

import com.example.themixologist.data.remote.CocktailDto
import com.example.themixologist.domain.model.Cocktail

import com.example.themixologist.domain.model.Ingredient

fun CocktailDto.toDomain(): Cocktail {
    val mappedIngredients = mutableListOf<Ingredient>()

    val rawIngredients = listOf(
        this.strIngredient1 to this.strMeasure1,
        this.strIngredient2 to this.strMeasure2,
        this.strIngredient3 to this.strMeasure3,
        this.strIngredient4 to this.strMeasure4,
        this.strIngredient5 to this.strMeasure5,
        this.strIngredient6 to this.strMeasure6,
        this.strIngredient7 to this.strMeasure7,
        this.strIngredient8 to this.strMeasure8,
        this.strIngredient9 to this.strMeasure9,
        this.strIngredient10 to this.strMeasure10,
        this.strIngredient11 to this.strMeasure11,
        this.strIngredient12 to this.strMeasure12,
        this.strIngredient13 to this.strMeasure13,
        this.strIngredient14 to this.strMeasure14,
        this.strIngredient15 to this.strMeasure15
    )

    rawIngredients.forEach { (ingredientName, measure) ->
        if (!ingredientName.isNullOrBlank()) {
            mappedIngredients.add(Ingredient(
                name = ingredientName.trim(), 
                measure = measure?.trim() ?: ""
            ))
        }
    }

    val instructionsList = this.instructions?.split(Regex("(?<=\\.)\\s+"))
        ?.map { it.trim() }
        ?.filter { it.isNotBlank() }
        ?: emptyList()

    return Cocktail(
        id = this.id ?: "",
        name = this.name ?: "Unknown Cocktail",
        imageUrl = this.thumbUrl ?: "",
        category = this.category ?: "Misc",
        alcoholic = this.alcoholic ?: "Unknown",
        glass = this.glass ?: "Unknown",
        instructions = if (instructionsList.isEmpty()) listOf("No instructions available.") else instructionsList,
        ingredients = mappedIngredients
    )
}


