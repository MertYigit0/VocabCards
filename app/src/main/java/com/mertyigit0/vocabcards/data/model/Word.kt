package com.mertyigit0.vocabcards.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Word(val english: String, val turkish: String) : Parcelable
