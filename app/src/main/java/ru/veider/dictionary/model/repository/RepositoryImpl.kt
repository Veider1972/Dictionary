package ru.veider.dictionary.model.repository

import ru.veider.dictionary.model.data.Dictionary
import ru.veider.dictionary.model.datasource.DataSourceImpl

class RepositoryImpl : Repository {
    private val dataSource = DataSourceImpl()
    override suspend fun findWords(word: String): List<Dictionary> = dataSource.getDictionaryData(word)
}