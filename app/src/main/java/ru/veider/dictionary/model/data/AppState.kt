package ru.veider.dictionary.model.data

sealed class AppState {
    data class Success(val data: List<Dictionary>?) : AppState()
    data class Error(val error: Throwable) : AppState()
}
