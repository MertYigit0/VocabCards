package com.mertyigit0.vocabcards

import android.content.Context
import android.content.SharedPreferences

object PrefsHelper {

    private const val PREFS_NAME = "vocabcards_prefs"
    private const val KEY_LEARNED_WORDS = "learned_words"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun addLearnedWord(context: Context, word: Word) {
        val sharedPreferences = getSharedPreferences(context)
        val learnedWords = sharedPreferences.getStringSet(KEY_LEARNED_WORDS, mutableSetOf()) ?: mutableSetOf()
        val updatedLearnedWords = learnedWords.toMutableSet()
        updatedLearnedWords.add(word.english)
        sharedPreferences.edit().putStringSet(KEY_LEARNED_WORDS, updatedLearnedWords).apply()
    }


    fun removeLearnedWord(context: Context, word: Word) {
        val sharedPreferences = getSharedPreferences(context)
        val learnedWords = sharedPreferences.getStringSet(KEY_LEARNED_WORDS, mutableSetOf()) ?: mutableSetOf()
        val updatedLearnedWords = learnedWords.toMutableSet()
        updatedLearnedWords.remove(word.english)
        sharedPreferences.edit().putStringSet(KEY_LEARNED_WORDS, updatedLearnedWords).apply()
    }


    fun getLearnedWords(context: Context): Set<String> {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getStringSet(KEY_LEARNED_WORDS, mutableSetOf()) ?: mutableSetOf()
    }

}
