package ru.veider.dictionary.model.data

data class Meanings(
    val id : Int,
    val partOfSpeechCode : String,
    val translation : Translation,
    val previewUrl : String,
    val imageUrl : String,
    val transcription : String,
    val soundUrl : String
)