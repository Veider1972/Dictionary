package ru.veider.dictionary.di

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.veider.dictionary.model.datasource.*
import ru.veider.dictionary.model.repository.Repository
import ru.veider.dictionary.model.repository.RepositoryImpl
import ru.veider.dictionary.presentation.viewmodel.DictionaryViewModel

val appModule = module {
    singleOf(::DataSourceImpl) { bind<DataSource>() }
    singleOf(::RepositoryImpl) { bind<Repository>() }
    singleOf(::DictionaryViewModel)
}

val apiModule = module {
    fun provideApi(retrofit: Retrofit): RemoteApi {
        return retrofit.create(RemoteApi::class.java)
    }
    single { provideApi(get()) }
}

val netModule = module {

    val webPath = "https://dictionary.skyeng.ru/api/public/v1/"

    fun createOkHttpClient(interceptor: Interceptor): OkHttpClient  = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }.build()

    fun provideRetrofit() =
            Retrofit.Builder()
                .baseUrl(webPath)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient {
                    Log.d("TAG", it.request().toString())
                    it.proceed(it.request())
                })
                .build()
                .create(RemoteApi::class.java)

    single { provideRetrofit() }
}
