package com.mertyigit0.vocabcards

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/entries/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: DictionaryApiService by lazy {
        retrofit.create(DictionaryApiService::class.java)
    }
}
