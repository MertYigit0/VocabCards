package com.mertyigit0.vocabcards

import android.content.Context

class WordRepository(private val context: Context) {

    private val allWords = listOf(
        Word("Apple", "Elma"),
        Word("Book", "Kitap"),
        Word("Car", "Araba"),
        Word("Dog", "Köpek")
    )

    fun getAllWords(): List<Word> = allWords

    fun getLearnedWords(): List<Word> {
        val learnedWordsSet = PrefsHelper.getLearnedWords(context)
        return allWords.filter { learnedWordsSet.contains(it.english) }
    }

    fun getRemainingWords(): List<Word> {
        val learnedWordsSet = PrefsHelper.getLearnedWords(context)
        return allWords.filter { !learnedWordsSet.contains(it.english) }
    }
}
