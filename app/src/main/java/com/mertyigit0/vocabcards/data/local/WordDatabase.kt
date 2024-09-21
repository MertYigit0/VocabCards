package com.mertyigit0.vocabcards.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.mertyigit0.vocabcards.data.model.Category
import com.mertyigit0.vocabcards.data.model.CategoryDao
import com.mertyigit0.vocabcards.data.model.Word
import com.mertyigit0.vocabcards.data.model.WordDao

@Database(entities = [Word::class, Category::class], version = 4, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun categoryDao(): CategoryDao

}
