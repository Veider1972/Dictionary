package ru.veider.dictionary.model.datasource

import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin
import ru.veider.dictionary.di.DATASOURCE_SCOPE
import ru.veider.dictionary.model.data.Dictionary

class DataSourceImpl : DataSource {

    private val scope = getKoin().createScope(this.toString(),named(DATASOURCE_SCOPE))
    private val provider = scope.get<RemoteApi>()

    override suspend fun getDictionaryData(word: String): List<Dictionary> = provider.getDictionaryData(word)
}