package com.mertyigit0.vocabcards.ui.worddetail

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mertyigit0.vocabcards.data.model.Word
import com.mertyigit0.vocabcards.data.model.WordResponse
import com.mertyigit0.vocabcards.data.repository.WordRepository
//import com.mertyigit0.vocabcards.data.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class WordDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WordRepository(application)
    private val _isLearned = MutableLiveData<Boolean>()
    val isLearned: LiveData<Boolean> get() = _isLearned
    val wordDetail = MutableLiveData<WordResponse?>()

    private var mediaPlayer: MediaPlayer? = null


    fun checkIfWordIsLearned(word: Word) {
        viewModelScope.launch {
            val learnedWords = repository.getLearnedWords()
            _isLearned.value = learnedWords.contains(word)
        }
    }


    fun toggleWordLearningStatus(word: Word) {
        viewModelScope.launch {
            val learnedWords = repository.getLearnedWords()
            if (learnedWords.contains(word)) {
                repository.removeLearnedWord(word)
            } else {
                repository.addLearnedWord(word)
            }
            checkIfWordIsLearned(word)
        }
    }

/*
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
*/

    fun playAudio(url: String) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(url)
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
    }
}
