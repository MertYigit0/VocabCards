package com.mertyigit0.vocabcards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WordDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _isLearned = MutableLiveData<Boolean>()
    val isLearned: LiveData<Boolean> get() = _isLearned

    private val prefsHelper = PrefsHelper

    fun checkIfWordIsLearned(word: Word) {
        val learnedWords = prefsHelper.getLearnedWords(getApplication())
        _isLearned.value = learnedWords.contains(word.english)
    }

    fun toggleWordLearningStatus(word: Word) {
        val learnedWords = prefsHelper.getLearnedWords(getApplication())
        if (learnedWords.contains(word.english)) {
            prefsHelper.removeLearnedWord(getApplication(), word)
        } else {
            prefsHelper.addLearnedWord(getApplication(), word)
        }
        // Update the learned status after toggling
        checkIfWordIsLearned(word)
    }
}
