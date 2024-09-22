package com.mertyigit0.vocabcards.ui.wordlist

import android.app.Application
import android.util.Log
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

    // Kategori ID'sini saklayacak bir değişken
    private var categoryId: Int? = null

    // Kategori ID'sini ayarlamak için bir yöntem
    fun setCategoryId(id: Int) {
        categoryId = id
        updateWordList()
    }

    init {
        loadWordsIfNeeded()
    }

    private fun loadWordsIfNeeded() {
        viewModelScope.launch {
            val words = repository.getAllWords()
            Log.d("WordListViewModela", "All words from database: $words")
            if (words.isEmpty()) {
                repository.loadWordsFromJson()
                updateWordList()
            }
        }
    }

    fun updateWordList() {
        viewModelScope.launch {
            val allWords = categoryId?.let { repository.getWordsByCategory(it) }?.shuffled() ?: emptyList()
            Log.d("WordListViewModela", "All words from category: $allWords")
            _wordList.value = allWords.filter { !it.isLearned }
        }
    }


    fun shuffleWords() {
        _wordList.value = _wordList.value?.shuffled()
    }

    fun searchWord(query: String) {
        viewModelScope.launch {
            _wordList.value = repository.searchWords(query)
        }
    }


    fun fetchWords() {
        viewModelScope.launch {
            repository.loadWordsFromFirestore()
        }
    }
}
