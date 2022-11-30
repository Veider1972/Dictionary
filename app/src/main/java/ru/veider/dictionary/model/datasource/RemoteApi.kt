package ru.veider.dictionary.model.datasource

import retrofit2.http.GET
import retrofit2.http.Query
import ru.veider.dictionary.model.data.Dictionary

interface RemoteApi {
    @GET("words/search")
    suspend fun getDictionaryData(@Query("search") word: String): List<Dictionary>
}