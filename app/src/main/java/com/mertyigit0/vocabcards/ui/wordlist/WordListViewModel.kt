package com.mertyigit0.vocabcards.ui.wordlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mertyigit0.vocabcards.data.model.Word
import com.mertyigit0.vocabcards.data.repository.WordRepository
import kotlinx.coroutines.launch

class WordListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WordRepository(application)
    private val _wordList = MutableLiveData<List<Word>>()
    val wordList: LiveData<List<Word>> get() = _wordList

    init {

        loadWordsIfNeeded()
        updateWordList()
    }

    private fun loadWordsIfNeeded() {
        viewModelScope.launch {
            val words = repository.getAllWords()
            if (words.isEmpty()) {
                repository.loadWordsFromJson()
                updateWordList()
            }
        }
    }

    fun updateWordList() {
        viewModelScope.launch {
            val allWords = repository.getAllWords().shuffled()
            // Öğrenilmemiş kelimeleri filtreleyin
            _wordList.value = allWords.filter { !it.isLearned }
        }
    }

    fun shuffleWords() {
        _wordList.value = _wordList.value?.shuffled()
    }

    // Arama sorgusu için yeni fonksiyon
    fun searchWord(query: String) {
        viewModelScope.launch {
            _wordList.value = repository.searchWords(query)
        }
    }

}
