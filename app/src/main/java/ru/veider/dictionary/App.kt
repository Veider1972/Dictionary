package ru.veider.dictionary

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.veider.dictionary.di.appModule
import ru.veider.dictionary.di.apiModule
import ru.veider.dictionary.di.netModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule, apiModule, netModule)
        }
    }
}
