package com.mertyigit0.vocabcards.ui.learnedlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.data.model.Word
import com.mertyigit0.vocabcards.data.repository.WordRepository
import kotlinx.coroutines.launch

class LearnedListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WordRepository(application)
    private val _learnedWords = MutableLiveData<List<Word>>()
    val learnedWords: LiveData<List<Word>> get() = _learnedWords

    // Yeni bir LiveData ekleyin
    private val _noWordsMessage = MutableLiveData<String>()
    val noWordsMessage: LiveData<String> get() = _noWordsMessage

    init {
        updateLearnedWords()
    }

    fun updateLearnedWords() {
        viewModelScope.launch {
            val learnedWords = repository.getLearnedWords()
            _learnedWords.value = learnedWords

            // Kelime sayısını kontrol et
            if (learnedWords.isEmpty()) {
                _noWordsMessage.value = getApplication<Application>().getString(R.string.no_words_learned)
            } else {
                _noWordsMessage.value = "" // Mesajı gizle
            }
        }
    }

    fun searchLearnedWord(query: String) {
        viewModelScope.launch {
            val result = repository.searchLearnedWords(query)
            _learnedWords.value = result

            // Kelime sayısını kontrol et
            _noWordsMessage.value = if (result.isEmpty()) {
                getApplication<Application>().getString(R.string.no_words_learned)
            } else {
                null // Mesajı gizle
            }
        }
    }
}
