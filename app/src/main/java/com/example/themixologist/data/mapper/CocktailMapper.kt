package com.example.themixologist.data.mapper

import com.example.themixologist.data.remote.CocktailDto
import com.example.themixologist.domain.model.Cocktail

fun CocktailDto.toDomain(): Cocktail {
    val mappedIngredients = mutableListOf<String>()

    val rawIngredients = mutableListOf<Pair<String?, String?>>()

    for (i in 1..15) {
        val ingredient = this::class.members
            .firstOrNull { it.name == "strIngredient$i" }
            ?.call(this) as? String

        val measure = this::class.members
            .firstOrNull { it.name == "strMeasure$i" }
            ?.call(this) as? String?

        if (!ingredient.isNullOrBlank()) {
            rawIngredients += ingredient to (measure ?: "")
        }
    }

    rawIngredients.forEach { (ingredient, measure) ->
        if (!ingredient.isNullOrBlank()) {
            val line = if(!measure.isNullOrBlank()) {
                "$measure $ingredient"
            } else {
                ingredient
            }
            mappedIngredients.add(line)
        }
    }

    return Cocktail(
        id = this.id ?: "",
        name = this.name ?: "Unknown Cocktail",
        imageUrl = this.thumbUrl ?: "",
        category = this.category ?: "Misc",
        instructions = this.instructions ?: "No instructions available.",
        ingredients = mappedIngredients

    )
}


