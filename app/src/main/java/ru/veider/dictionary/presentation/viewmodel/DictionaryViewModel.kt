package ru.veider.dictionary.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.veider.dictionary.model.data.AppState
import ru.veider.dictionary.model.repository.Repository

@OptIn(FlowPreview::class) class DictionaryViewModel(
    private val repo: Repository
) : ViewModel(), DictionaryViewModelContract {

    override val dictionaryData: MutableLiveData<AppState> = MutableLiveData()
    override val searchedWord: MutableLiveData<String> = MutableLiveData()

    private val findFlow = MutableStateFlow("")

    private val scopeIO = CoroutineScope(Dispatchers.IO)
    private val scopeMain = CoroutineScope(Dispatchers.Main)
    private var job: Job? = null

    init {
        CoroutineScope(Dispatchers.Main).launch {
            findFlow
                .debounce(300)
                .filter {
                    it.length > 3
                }
                .distinctUntilChanged()
                .collect { findWords(it) }
        }
    }

    private fun findWords(word: String) {
        searchedWord.postValue(word)
        job?.cancel()
        job = scopeIO.launch {
            try {
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

    override fun find(word: String) {
        findFlow.value = word
    }

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }
}