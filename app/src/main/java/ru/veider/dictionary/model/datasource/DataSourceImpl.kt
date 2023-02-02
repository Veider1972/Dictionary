package ru.veider.dictionary.model.datasource

import ru.veider.dictionary.model.data.Dictionary

class DataSourceImpl(
    private val provider: RemoteApi
) : DataSource {
    override suspend fun getDictionaryData(word: String): List<Dictionary> = provider.getDictionaryData(word)
}