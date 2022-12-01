package ru.veider.dictionary.model.datasource

import ru.veider.dictionary.model.data.Dictionary

class DataSourceImpl : DataSource {
    private val provider = RetrofitImpl()
    override suspend fun getDictionaryData(word: String): List<Dictionary> = provider.getDictionaryData(word)
}