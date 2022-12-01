package ru.veider.dictionary.presenter

import android.view.View
import io.reactivex.subjects.ReplaySubject
import kotlinx.coroutines.*
import ru.veider.dictionary.model.data.AppState
import ru.veider.dictionary.model.repository.RepositoryImpl

class DictionaryPresenterImpl : DictionaryPresenter {

    override val dictionaryData: ReplaySubject<AppState> = ReplaySubject.createWithSize(1)
    override val searchedWord: ReplaySubject<String> = ReplaySubject.createWithSize(1)

    override var view: View? = null
    private val repo = RepositoryImpl()

    override fun findWords(word: String) {
        searchedWord.onNext(word)
        CoroutineScope(Dispatchers.IO).launch {
            MainScope().launch {
                dictionaryData.onNext(AppState.Loading())
            }
            try {
                Thread.sleep(2000) // имитация длительной загрузки
                val words = repo.findWords(word)
                MainScope().launch {
                    dictionaryData.onNext(AppState.Success(words))
                }
            } catch (e: Exception) {
                MainScope().launch {
                    dictionaryData.onNext(AppState.Error(e))
                }
            }
        }
    }

    override fun attachView(view: View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}