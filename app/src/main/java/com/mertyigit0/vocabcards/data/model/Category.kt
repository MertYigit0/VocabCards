package com.mertyigit0.vocabcards.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey val id: Long ,
    val name: String
)
