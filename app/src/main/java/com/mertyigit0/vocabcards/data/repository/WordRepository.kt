package com.mertyigit0.vocabcards.data.repository

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.data.local.WordDatabase
import com.mertyigit0.vocabcards.data.model.Category
import com.mertyigit0.vocabcards.data.model.Word
import com.mertyigit0.vocabcards.data.model.WordListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

class WordRepository(private val context: Context) {

    private val db = Room.databaseBuilder(
        context.applicationContext,
        WordDatabase::class.java, "word_database"
    ).build()

    suspend fun loadWordsFromJson() {
        withContext(Dispatchers.IO) {
            val inputStream = context.resources.openRawResource(R.raw.words)
            val reader = InputStreamReader(inputStream)

            val wordListType = object : TypeToken<WordListResponse>() {}.type
            val wordListResponse: WordListResponse = Gson().fromJson(reader, wordListType)

            val words = wordListResponse.words.map { response ->
                Word(
                    english = response.word,
                    turkish = response.translations.turkish,
                    emoji = response.translations.emoji,
                    german = response.translations.german,
                    italian = response.translations.italian,
                    spanish = response.translations.spanish,
                    french = response.translations.french,
                    categoryId =response.categoryId

                )
            }
            db.wordDao().insertAll(words)
        }
    }

    suspend fun getAllWords(): List<Word> {
        return withContext(Dispatchers.IO) {
            db.wordDao().getAllWords()
        }
    }

    suspend fun getLearnedWords(): List<Word> {
        return withContext(Dispatchers.IO) {
            db.wordDao().getLearnedWords()
        }
    }

    suspend fun addLearnedWord(word: Word) {
        withContext(Dispatchers.IO) {
            val updatedWord = word.copy(isLearned = true)
            db.wordDao().updateWord(updatedWord)
        }
    }

    suspend fun removeLearnedWord(word: Word) {
        withContext(Dispatchers.IO) {
            val updatedWord = word.copy(isLearned = false)
            db.wordDao().updateWord(updatedWord)
        }
    }

    suspend fun searchWords(query: String): List<Word> {
        return withContext(Dispatchers.IO) {
            db.wordDao().searchWords("%$query%")
        }
    }

    suspend fun searchLearnedWords(query: String): List<Word> {
        return withContext(Dispatchers.IO) {
            db.wordDao().searchLearnedWords("%$query%")
        }
    }


    suspend fun insertCategory(category: Category) {
        withContext(Dispatchers.IO) {
            db.categoryDao().insert(category)
        }
    }

    // Tüm kategorileri almak için
    suspend fun getAllCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            db.categoryDao().getAllCategories()
        }
    }

    suspend fun loadCategoriesFromJson() {
        withContext(Dispatchers.IO) {
            val inputStream = context.resources.openRawResource(R.raw.category)
            val reader = InputStreamReader(inputStream)

            val categoryListType = object : TypeToken<List<Category>>() {}.type
            val categoryList: List<Category> = Gson().fromJson(reader, categoryListType)

            db.categoryDao().insertAll(categoryList) // Tüm kategorileri ekle
        }
    }


    // Kategoriye göre kelimeleri almak için
    suspend fun getWordsByCategory(categoryId: Long): List<Word> {
        return withContext(Dispatchers.IO) {
            db.wordDao().getWordsByCategory(categoryId) // DAO'da bu fonksiyon tanımlanmalı
        }
    }
}
