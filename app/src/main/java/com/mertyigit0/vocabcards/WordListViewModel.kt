package com.mertyigit0.vocabcards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordListViewModel : ViewModel() {

    // Kelime listesini LiveData ile tut
    private val _wordList = MutableLiveData<List<Word>>()
    val wordList: LiveData<List<Word>> get() = _wordList

    init {
        loadWords()
    }

    // Kelime listesini yükleyen fonksiyon
    private fun loadWords() {
        _wordList.value = listOf(
            Word("Apple", "Elma"),
            Word("Book", "Kitap"),
            Word("Car", "Araba"),
            Word("Dog", "Köpek"),
            Word("House", "Ev"),
            // Devamını ekle
        )
    }
}
