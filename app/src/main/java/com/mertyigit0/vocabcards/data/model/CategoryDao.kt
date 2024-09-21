package com.mertyigit0.vocabcards.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(category: Category)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<Category>

    @Insert
    suspend fun insertAll(categories: List<Category>): List<Long> // Ekleme sonuçlarını döndürür
}
