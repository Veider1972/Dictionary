package ru.veider.dictionary

import android.app.Application
import ru.veider.dictionary.presenter.DictionaryPresenterImpl

class App : Application() {

    val presenter = DictionaryPresenterImpl()

    companion object {
        val instance: App by lazy { App() }
    }
}

val app get() = App.instance