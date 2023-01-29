package ru.veider.dictionary.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.veider.dictionary.model.data.AppState
import ru.veider.dictionary.model.repository.RepositoryImpl
import ru.veider.dictionary.presentation.view.presenter.DictionaryViewModelContract

class DictionaryViewModel : ViewModel(), DictionaryViewModelContract {

    override val dictionaryData: MutableLiveData<AppState> = MutableLiveData()
    override val searchedWord: MutableLiveData<String> = MutableLiveData()

    private val repo = RepositoryImpl()

    override fun findWords(word: String) {
        searchedWord.postValue(word)
        CoroutineScope(Dispatchers.IO).launch {
            MainScope().launch {
                dictionaryData.postValue(AppState.Loading())
            }
            try {
                Thread.sleep(2000) // имитация длительной загрузки
                val words = repo.findWords(word)
                MainScope().launch {
                    dictionaryData.postValue(AppState.Success(words))
                }
            } catch (e: Exception) {
                MainScope().launch {
                    dictionaryData.postValue(AppState.Error(e))
                }
            }
        }
    }
}