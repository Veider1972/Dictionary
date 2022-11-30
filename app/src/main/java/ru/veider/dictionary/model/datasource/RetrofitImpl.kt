package ru.veider.dictionary.model.datasource

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.veider.dictionary.model.data.Dictionary
import java.io.IOException

const val WEB_PATH = "https://dictionary.skyeng.ru/api/public/v1/"

class RetrofitImpl : DataSource {

    private val apiService = Retrofit.Builder()
        .baseUrl(WEB_PATH)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient(DebugInterceptor()))
        .build()
        .create(RemoteApi::class.java)

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return okHttpClient.build()
    }

    inner class DebugInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            Log.d("TAG", chain.request().toString())
            return chain.proceed(chain.request())
        }
    }

    override suspend fun getDictionaryData(word: String): List<Dictionary> =
            apiService.getDictionaryData(word)
}