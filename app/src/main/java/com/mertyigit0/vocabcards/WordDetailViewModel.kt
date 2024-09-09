package com.mertyigit0.vocabcards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WordDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _isLearned = MutableLiveData<Boolean>()
    val isLearned: LiveData<Boolean> get() = _isLearned
    val wordDetail = MutableLiveData<WordResponse?>()


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

    fun fetchWordDetails(word: String) {
        RetrofitInstance.api.getWordDetails(word).enqueue(object : Callback<List<WordResponse>> {
            override fun onResponse(
                call: Call<List<WordResponse>>,
                response: Response<List<WordResponse>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    wordDetail.value = response.body()!!.firstOrNull()
                }
            }

            override fun onFailure(call: Call<List<WordResponse>>, t: Throwable) {
                wordDetail.value = null
            }
        })
    }
}
