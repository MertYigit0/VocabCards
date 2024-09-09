package com.mertyigit0.vocabcards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LearnedListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WordRepository(application)
    private val _learnedWords = MutableLiveData<List<Word>>()
    val learnedWords: LiveData<List<Word>> get() = _learnedWords

    init {
        updateLearnedWords()
    }

    fun updateLearnedWords() {
        _learnedWords.value = repository.getLearnedWords()
    }
}
