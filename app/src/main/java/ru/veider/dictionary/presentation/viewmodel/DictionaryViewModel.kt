package ru.veider.dictionary.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.veider.dictionary.model.data.AppState
import ru.veider.dictionary.model.repository.Repository

class DictionaryViewModel(
    private val repo: Repository
) : ViewModel(), DictionaryViewModelContract {

    override val dictionaryData: MutableLiveData<AppState> = MutableLiveData()
    override val searchedWord: MutableLiveData<String> = MutableLiveData()

    private val scopeIO = CoroutineScope(Dispatchers.IO)
    private val scopeMain = CoroutineScope(Dispatchers.Main)
    private var job: Job? = null

    override fun findWords(word: String) {
        searchedWord.postValue(word)
        job?.cancel()
        job = scopeIO.launch {
            dictionaryData.postValue(AppState.Loading())
            try {
                delay(2000) // имитация длительной загрузки
                val words = repo.findWords(word)
                run {
                    dictionaryData.postValue(AppState.Success(words))
                }
            } catch (e: Exception) {
                scopeMain.launch {
                    dictionaryData.postValue(AppState.Error(e))
                }
            }
        }
    }

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }
}