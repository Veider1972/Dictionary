package ru.veider.dictionary

import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.veider.dictionary.model.data.Dictionary
import ru.veider.dictionary.model.repository.Repository
import ru.veider.dictionary.presenter.DictionaryPresenter
import ru.veider.dictionary.presenter.DictionaryPresenterImpl

class PresenterTest {

    private lateinit var dictionaryPresenter: DictionaryPresenter

    @Mock
    private lateinit var repo: Repository

    @Mock
    private lateinit var dictionary: Dictionary

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dictionaryPresenter = DictionaryPresenterImpl(repo)
        dictionaryPresenter.findWords("word")
    }

    @Test
    fun findWordPresenter_Test() =
        runBlocking {
            whenever(repo.findWords(anyString())).thenReturn(listOf(dictionary, dictionary))
            Assert.assertEquals(repo.findWords("").size, 2)
            Assert.assertEquals(dictionaryPresenter.searchedWord, "word")
            Assert.assertNotNull(dictionaryPresenter.searchedWord)
        }


}