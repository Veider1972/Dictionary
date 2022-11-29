package ru.veider.dictionary.model

interface Repo {
    fun getWords(word:String):List<String>
}