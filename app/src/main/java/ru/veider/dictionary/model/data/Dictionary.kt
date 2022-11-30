package ru.veider.dictionary.model.data

data class Dictionary(
    val id: Int,
    val text: String,
    val meanings: List<Meanings> = ArrayList()
)