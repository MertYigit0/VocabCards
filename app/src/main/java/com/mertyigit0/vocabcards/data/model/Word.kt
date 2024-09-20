package com.mertyigit0.vocabcards.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "word_table")
@Parcelize
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val english: String,
    val turkish: String,
    val emoji: String? = null,
    val german: String? = null,
    val italian: String? = null,
    val spanish: String? = null,
    val french: String? = null,
    var isLearned: Boolean = false
) : Parcelable

data class WordJsonResponse(
    @SerializedName("word") val word: String,
    @SerializedName("translations") val translations: Translations
)

data class Translations(
    @SerializedName("tr") val turkish: String,
    @SerializedName("emoji") val emoji: String,
    @SerializedName("de") val german: String,
    @SerializedName("it") val italian: String,
    @SerializedName("es") val spanish: String,
    @SerializedName("fr") val french: String
)

data class WordListResponse(
    val words: List<WordJsonResponse>
)


