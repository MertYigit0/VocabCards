package com.mertyigit0.vocabcards

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordListViewModel(application: Application) : AndroidViewModel(application) {

    private val _wordList = MutableLiveData<List<Word>>()
    val wordList: LiveData<List<Word>> get() = _wordList

    private val allWords = listOf(
        Word("Apple", "Elma"),
        Word("Book", "Kitap"),
        Word("Car", "Araba"),
        Word("Dog", "Köpek")
    )

    init {
        loadWords()
    }

    private fun loadWords() {
        // Öğrenilmiş kelimeleri almak
        val learnedWords = PrefsHelper.getLearnedWords(getApplication())
        // Güncellenmiş listeyi ayarla
        _wordList.value = getShuffledWords(learnedWords)
    }

    private fun getShuffledWords(learnedWords: Set<String>): List<Word> {
        val remainingWords = allWords.filter { !learnedWords.contains(it.english) }
        return remainingWords.shuffled()  // Öğrenilmemiş kelimeleri rastgele sırala
    }

    fun shuffleWords() {
        val learnedWords = PrefsHelper.getLearnedWords(getApplication())
        _wordList.value = getShuffledWords(learnedWords)
    }

    fun removeWord(word: Word) {
        val updatedList = _wordList.value?.filter { it != word }
        _wordList.value = updatedList
    }

    fun addWord(word: Word) {
        val updatedList = _wordList.value?.toMutableList() ?: mutableListOf()
        updatedList.add(word)
        _wordList.value = updatedList
    }

    fun updateWordList() {
        val learnedWords = PrefsHelper.getLearnedWords(getApplication())
        _wordList.value = getShuffledWords(learnedWords)
    }
}



