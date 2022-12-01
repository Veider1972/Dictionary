package ru.veider.dictionary.presenter

import android.view.View
import io.reactivex.subjects.ReplaySubject
import ru.veider.dictionary.model.data.AppState

interface DictionaryPresenter {
    var view: View?
    val dictionaryData: ReplaySubject<AppState>
    val searchedWord: ReplaySubject<String>
    fun attachView(view: View)
    fun detachView()
    fun findWords(word: String)
}