package com.example.themixologist.domain.model

data class Cocktail(
    val id: String,
    val name: String,
    val imageUrl: String,
    val category: String,
    val instructions: String,
    val ingredients: List<String>
)
