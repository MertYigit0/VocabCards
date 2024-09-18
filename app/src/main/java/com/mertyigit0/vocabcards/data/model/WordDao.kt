package com.mertyigit0.vocabcards.data.model

import androidx.room.Dao

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mertyigit0.vocabcards.data.model.Word

@Dao
interface WordDao {

    @Insert
    suspend fun insertAll(words: List<Word>)

    @Query("SELECT * FROM word_table")
    suspend fun getAllWords(): List<Word>

    // Öğrenilen kelimeleri getirir
    @Query("SELECT * FROM word_table WHERE isLearned = 1")
    suspend fun getLearnedWords(): List<Word>

    // Kelime güncelleme (öğrenildi mi öğrenilmedi mi güncellemek için)
    @Update
    suspend fun updateWord(word: Word)
}
