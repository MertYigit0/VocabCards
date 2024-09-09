package com.mertyigit0.vocabcards


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LearnedListViewModel(application: Application) : AndroidViewModel(application) {

    private val _learnedWords = MutableLiveData<List<Word>>()
    val learnedWords: LiveData<List<Word>> get() = _learnedWords

    init {
        loadLearnedWords()
    }

    private fun loadLearnedWords() {
        val learnedWordsSet = PrefsHelper.getLearnedWords(getApplication())
        // Convert to Word objects, adjust according to your data source
        _learnedWords.value = learnedWordsSet.map { Word(it, "Translation") } // Adjust translation accordingly
    }

    fun updateLearnedWords() {
        loadLearnedWords()
    }
}
