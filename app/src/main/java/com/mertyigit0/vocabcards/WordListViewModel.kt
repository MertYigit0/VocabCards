package com.mertyigit0.vocabcards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordListViewModel : ViewModel() {

    private val _wordList = MutableLiveData<List<Word>>()
    val wordList: LiveData<List<Word>> get() = _wordList

    init {
        loadWords()
    }

    private fun loadWords() {
        _wordList.value = getShuffledWords()
    }

    private fun getShuffledWords(): List<Word> {
        val words = listOf(
            Word("Apple", "Elma"),
            Word("Book", "Kitap"),
            Word("Car", "Araba"),
            Word("Dog", "Köpek"),
            Word("House", "Ev"),
            // Diğer kelimeleri ekle
        )
        return words.shuffled()  // Kelimeleri rastgele sırala
    }

    // Rastgele sıralama fonksiyonu
    fun shuffleWords() {
        _wordList.value = getShuffledWords()
    }
}
