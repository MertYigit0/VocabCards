package com.mertyigit0.vocabcards.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Word(
    val english: String,
    val turkish: String,
    val emoji: String? = null,
    val german: String? = null,
    val italian: String? = null,
    val spanish: String? = null,
    val french: String? = null
) : Parcelable
