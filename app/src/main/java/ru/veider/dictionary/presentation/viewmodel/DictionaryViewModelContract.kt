package ru.veider.dictionary.presentation.view.presenter

import androidx.lifecycle.LiveData
import ru.veider.dictionary.model.data.AppState

interface DictionaryViewModelContract {
    val dictionaryData: LiveData<AppState>
    val searchedWord: LiveData<String>
    fun findWords(word: String)
}