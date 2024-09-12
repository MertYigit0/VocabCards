package com.mertyigit0.vocabcards.data.repository

import android.content.Context
import com.mertyigit0.vocabcards.data.local.PrefsHelper
import com.mertyigit0.vocabcards.data.model.Word

class WordRepository(private val context: Context) {

    private val allWords = listOf(
        Word("Apple", "Elma"),
        Word("Book", "Kitap"),
        Word("Car", "Araba"),
        Word("Dog", "Köpek"),
        Word("Glass", "Bardak"),
        Word("Window", "Pencere"),
        Word("Door", "Kapı"),
        Word("Street", "Sokak"),
        Word("City", "Şehir"),
        Word("Country", "Ülke"),
        Word("Map", "Harita"),
        Word("Bag", "Çanta"),
        Word("Shoes", "Ayakkabı"),
        Word("Shirt", "Gömlek"),
        Word("Pants", "Pantolon"),
        Word("Jacket", "Ceket"),
        Word("Hat", "Şapka"),
        Word("Bed", "Yatak"),
        Word("Lamp", "Lamba"),
        Word("Clock", "Saat"),
        Word("Mirror", "Ayna"),
        Word("Fan", "Vantilatör"),
        Word("Refrigerator", "Buzdolabı"),
        Word("Washing Machine", "Çamaşır Makinesi"),
        Word("Oven", "Fırın"),
        Word("Stove", "Ocak"),
        Word("Sink", "Lavabo"),
        Word("Toothbrush", "Diş Fırçası"),
        Word("Toothpaste", "Diş Macunu"),
        Word("Soap", "Sabun"),
        Word("Towel", "Havlu"),
        Word("Shampoo", "Şampuan"),
        Word("Conditioner", "Saç Kremi"),
        Word("Bread", "Ekmek"),
        Word("Butter", "Tereyağı"),
        Word("Milk", "Süt"),
        Word("Cheese", "Peynir"),
        Word("Egg", "Yumurta"),
        Word("Chicken", "Tavuk"),
        Word("Fish", "Balık"),
        Word("Meat", "Et"),
        Word("Rice", "Pirinç"),
        Word("Pasta", "Makarna"),
        Word("Fruit", "Meyve"),
        Word("Vegetable", "Sebze"),
        Word("Juice", "Meyve Suyu"),
        Word("Coffee", "Kahve"),
        Word("Tea", "Çay")
    )

    fun getAllWords(): List<Word> = allWords

    fun getLearnedWords(): List<Word> {
        val learnedWordsSet = PrefsHelper.getLearnedWords(context)
        return allWords.filter { learnedWordsSet.contains(it.english) }
    }

    fun getRemainingWords(): List<Word> {
        val learnedWordsSet = PrefsHelper.getLearnedWords(context)
        return allWords.filter { !learnedWordsSet.contains(it.english) }
    }
}
