package com.mertyigit0.vocabcards

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class WordDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _isLearned = MutableLiveData<Boolean>()
    val isLearned: LiveData<Boolean> get() = _isLearned
    val wordDetail = MutableLiveData<WordResponse?>()

    private val prefsHelper = PrefsHelper
    private var mediaPlayer: MediaPlayer? = null

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

    fun playAudio(url: String) {
        mediaPlayer?.release() // Önceki örneği serbest bırak
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(url) // URL'yi ayarla
                prepare() // Hazırla
                start() // Başlat
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release() // Fragment yok edilirken media player'ı serbest bırak
    }
}
