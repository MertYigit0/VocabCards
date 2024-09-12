package com.mertyigit0.vocabcards.data.network

import com.mertyigit0.vocabcards.data.model.WordResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService {
    @GET("en/{word}")
    fun getWordDetails(@Path("word") word: String): Call<List<WordResponse>>
}
