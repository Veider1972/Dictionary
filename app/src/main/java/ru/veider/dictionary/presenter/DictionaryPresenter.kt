package ru.veider.dictionary.presenter

import androidx.fragment.app.Fragment

interface DictionaryPresenter {
    fun attachView(fragment:Fragment)
    fun detachView()
    fun getWords(word:String):List<String>
}