package com.mertyigit0.vocabcards.data.repository

import android.content.Context
import android.util.Log
import androidx.room.Room
//import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.data.local.WordDatabase
import com.mertyigit0.vocabcards.data.model.Category
import com.mertyigit0.vocabcards.data.model.Word
import com.mertyigit0.vocabcards.data.model.WordListResponse
import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.tasks.await
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
/*
    suspend fun loadCategoriesFromFirestore(): List<Category> {
        return withContext(Dispatchers.IO) {
            try {
                val firestore = FirebaseFirestore.getInstance()

                // Firestore'dan kategorileri çek
                val result = firestore.collection("categories").get().await()

                // Verileri map ile Category objelerine çevir
                val categories = result.map { document ->
                    // 'id' değerini güvenli bir şekilde alma
                    val idValue = document.get("id")
                    val id = when (idValue) {
                        is Number -> idValue.toInt()  // Eğer id Number ise Int'e çevir
                        is String -> idValue.toIntOrNull() ?: 0  // Eğer id String ise ve sayıya çevrilebiliyorsa çevir
                        else -> 0  // Diğer durumlarda varsayılan olarak 0
                    }

                    Category(
                        id = id,
                        name = document.getString("name") ?: "",
                        emoji = document.getString("emoji") ?: ""
                    )
                }

                // Mevcut kategorileri kontrol et
                val existingCategories = db.categoryDao().getAllCategories() // Tüm kategorileri al
                val existingCategorySet = existingCategories.map { it.name }.toSet() // Mevcut kategorileri Set olarak al

                // Yeni kategorileri filtrele
                val newCategories = categories.filter { it.name !in existingCategorySet }

                // Room veritabanına yalnızca yeni kategorileri kaydet
                if (newCategories.isNotEmpty()) {
                    db.categoryDao().insertAll(newCategories)
                }

                // Yeni kategorileri döndür
                return@withContext newCategories
            } catch (e: Exception) {
                Log.e("FirestoreError", "Kategoriler Firestore'dan çekilirken hata oluştu", e)
                return@withContext emptyList()
            }
        }
    }
*/


/*
    suspend fun loadWordsFromFirestore(): List<Word> {
        return withContext(Dispatchers.IO) {
            try {
                val firestore = FirebaseFirestore.getInstance()

                // Firestore'dan kelimeleri çek
                val result = firestore.collection("words").get().await()

                // Verileri map ile Word objelerine çevir
                val words = result.map { document ->
                    val translationsData = document.get("translations") as Map<String, String>
                    Word(
                        english = document.getString("word") ?: "",
                        turkish = translationsData["tr"] ?: "",
                        emoji = translationsData["emoji"],
                        german = translationsData["de"],
                        italian = translationsData["it"],
                        spanish = translationsData["es"],
                        french = translationsData["fr"],
                        categoryId = document.getLong("categoryId") ?: 0L
                    )
                }

                // Mevcut kelimeleri kontrol et ve ekle
                val existingWords = db.wordDao().getAllWords() // Tüm kelimeleri al
                val existingWordSet = existingWords.map { it.english }.toSet() // Mevcut kelimeleri Set olarak al

                // Yeni kelimeleri filtrele
                val newWords = words.filter { it.english !in existingWordSet }

                // Room veritabanına yalnızca yeni kelimeleri kaydet
                if (newWords.isNotEmpty()) {
                    db.wordDao().insertAll(newWords)
                }

                // Yeni kelimeleri döndür
                return@withContext newWords
            } catch (e: Exception) {
                Log.e("FirestoreError", "Kelimeler Firestore'dan çekilirken hata oluştu", e)
                return@withContext emptyList()
            }
        }
    }

*/



    // Kategoriye göre kelimeleri almak için
    suspend fun getWordsByCategory(categoryId: Int): List<Word> {
        return withContext(Dispatchers.IO) {
            db.wordDao().getWordsByCategory(categoryId) // DAO'da bu fonksiyon tanımlanmalı
        }
    }

    suspend fun getWordCountByCategory(categoryId: Int): Int {
        return withContext(Dispatchers.IO) {
            db.wordDao().getWordsByCategory(categoryId).size // Bu fonksiyonun DAO'da tanımlanması gerekir
        }
    }
}


