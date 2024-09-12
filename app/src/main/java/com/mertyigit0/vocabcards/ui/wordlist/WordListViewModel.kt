package com.mertyigit0.vocabcards.ui.wordlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mertyigit0.vocabcards.data.model.Word
import com.mertyigit0.vocabcards.data.repository.WordRepository


class WordListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WordRepository(application)
    private val _wordList = MutableLiveData<List<Word>>()
    val wordList: LiveData<List<Word>> get() = _wordList

    init {
        updateWordList()
    }

    fun shuffleWords() {
        _wordList.value = repository.getRemainingWords().shuffled()
    }

    fun updateWordList() {
        _wordList.value = repository.getRemainingWords().shuffled()
    }
}




