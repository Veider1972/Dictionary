package ru.veider.dictionary.model.datasource

import ru.veider.dictionary.model.data.Dictionary

class RetrofitImpl(
    private val apiService: RemoteApi
) : DataSource {

    override suspend fun getDictionaryData(word: String): List<Dictionary> =
            apiService.getDictionaryData(word)
}