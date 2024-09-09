package com.mertyigit0.vocabcards

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService {
    @GET("en/{word}")
    fun getWordDetails(@Path("word") word: String): Call<List<WordResponse>>
}
