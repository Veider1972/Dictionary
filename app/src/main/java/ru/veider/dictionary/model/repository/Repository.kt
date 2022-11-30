package ru.veider.dictionary.model.repository

import ru.veider.dictionary.model.data.Dictionary

interface Repository {
    suspend fun findWords(word:String):List<Dictionary>
}