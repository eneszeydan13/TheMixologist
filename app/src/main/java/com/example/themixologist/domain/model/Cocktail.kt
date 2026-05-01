package com.example.themixologist.domain.model

data class Cocktail(
    val id: String,
    val name: String,
    val imageUrl: String,
    val category: String,
    val alcoholic: String,
    val glass: String,
    val instructions: List<String>,
    val ingredients: List<Ingredient>
)
