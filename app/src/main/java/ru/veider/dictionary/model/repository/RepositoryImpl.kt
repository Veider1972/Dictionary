package ru.veider.dictionary.model.repository

import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin
import ru.veider.dictionary.di.REPOSITORY_SCOPE
import ru.veider.dictionary.model.data.Dictionary
import ru.veider.dictionary.model.datasource.DataSource

class RepositoryImpl : Repository {

    private val scope = getKoin().createScope(this.toString(),named(REPOSITORY_SCOPE))
    private val dataSource = scope.get<DataSource>()

    override suspend fun findWords(word: String): List<Dictionary> = dataSource.getDictionaryData(word)
}