package ru.veider.dictionary.presenter

import android.view.View
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import ru.veider.dictionary.model.data.AppState
import ru.veider.dictionary.model.repository.RepositoryImpl

class DictionaryPresenterImpl : DictionaryPresenter {

    override var dictionaryData: MutableLiveData<AppState> = MutableLiveData()
    private var storedSearchedWord = ""
    override val searchedWord: String
        get() = storedSearchedWord

    override var view: View? = null
    private val repo = RepositoryImpl()

    override fun findWords(word: String) {
        storedSearchedWord = word
        CoroutineScope(Dispatchers.IO).launch {
            dictionaryData.postValue(AppState.Loading())
            try {
                val words = repo.findWords(word)
                dictionaryData.postValue(AppState.Success(words))
            } catch (e: Exception) {
                dictionaryData.postValue(AppState.Error(e))
            }
        }
     }

    override fun attachView(view: View) {
        this.view = view
        dictionaryData.postValue(dictionaryData.value)
    }

    override fun detachView() {
        this.view = null
    }
}