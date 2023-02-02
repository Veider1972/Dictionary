package ru.veider.dictionary.model.repository

import ru.veider.dictionary.model.data.Dictionary
import ru.veider.dictionary.model.datasource.DataSource

class RepositoryImpl(
    private val dataSource: DataSource
) : Repository {
    override suspend fun findWords(word: String): List<Dictionary> = dataSource.getDictionaryData(word)
}