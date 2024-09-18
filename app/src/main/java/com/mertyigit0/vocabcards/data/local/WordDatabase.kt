package com.mertyigit0.vocabcards.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.mertyigit0.vocabcards.data.model.Word
import com.mertyigit0.vocabcards.data.model.WordDao

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}
