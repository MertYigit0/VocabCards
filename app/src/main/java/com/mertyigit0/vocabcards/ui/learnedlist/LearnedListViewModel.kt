package com.mertyigit0.vocabcards.ui.learnedlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mertyigit0.vocabcards.data.model.Word
import com.mertyigit0.vocabcards.data.repository.WordRepository
import kotlinx.coroutines.launch

class LearnedListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WordRepository(application)
    private val _learnedWords = MutableLiveData<List<Word>>()
    val learnedWords: LiveData<List<Word>> get() = _learnedWords

    init {
        updateLearnedWords()
    }

    fun updateLearnedWords() {
        viewModelScope.launch {
            val learnedWords = repository.getLearnedWords()
            _learnedWords.value = learnedWords
        }
    }

    // Arama sorgusu için yeni fonksiyon
    fun searchLearnedWord(query: String) {
        viewModelScope.launch {
            _learnedWords.value = repository.searchLearnedWords(query)
        }
    }
}
