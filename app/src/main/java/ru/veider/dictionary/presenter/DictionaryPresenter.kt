package ru.veider.dictionary.presenter

import android.view.View
import androidx.lifecycle.MutableLiveData
import ru.veider.dictionary.model.data.AppState

interface DictionaryPresenter {
    var view: View?
    val dictionaryData: MutableLiveData<AppState>
    val searchedWord: String
    fun attachView(view: View)
    fun detachView()
    fun findWords(word: String)
}