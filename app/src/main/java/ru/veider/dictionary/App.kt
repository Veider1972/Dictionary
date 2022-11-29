package ru.veider.dictionary

import android.app.Application
import ru.veider.dictionary.model.Repo
import ru.veider.dictionary.model.RepoImpl
import ru.veider.dictionary.presenter.DictionaryPresenter
import ru.veider.dictionary.presenter.DictionaryPresenterImpl

class App:Application() {
    val repo: Repo by lazy { RepoImpl() }
    val presenter: DictionaryPresenter by lazy { DictionaryPresenterImpl(repo) }
    companion object{
        val instance : App by lazy{App()}
    }
}

val app get() = App.instance