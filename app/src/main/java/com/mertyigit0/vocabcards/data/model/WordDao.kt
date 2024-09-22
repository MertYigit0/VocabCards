package com.mertyigit0.vocabcards.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {

    @Insert
    suspend fun insertAll(words: List<Word>)

    @Query("SELECT * FROM word_table")
    suspend fun getAllWords(): List<Word>

    @Query("SELECT * FROM word_table WHERE isLearned = 1")
    suspend fun getLearnedWords(): List<Word>

    @Update
    suspend fun updateWord(word: Word)

    @Query("SELECT * FROM word_table WHERE english LIKE '%' || :searchQuery || '%' AND isLearned = 0")
    suspend fun searchWords(searchQuery: String): List<Word>

    @Query("SELECT * FROM word_table WHERE english LIKE '%' || :searchQuery || '%' AND isLearned = 1")
    suspend fun searchLearnedWords(searchQuery: String): List<Word>

    @Insert
    suspend fun insert(word: Word)

    @Query("SELECT * FROM word_table WHERE categoryId = :categoryId")
    suspend fun getWordsByCategory(categoryId: Int): List<Word>

}






