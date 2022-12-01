package ru.veider.dictionary.model.datasource

import ru.veider.dictionary.model.data.Dictionary

interface DataSource {
    suspend fun getDictionaryData(word: String):List<Dictionary>
}