package com.mertyigit0.vocabcards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordListViewModel : ViewModel() {

    private val _wordList = MutableLiveData<List<Word>>()
    val wordList: LiveData<List<Word>> get() = _wordList

    init {
        loadWords()
    }

    private fun loadWords() {
        _wordList.value = getShuffledWords()
    }

    private fun getShuffledWords(): List<Word> {
        val words = listOf(
            Word("Apple", "Elma"),
            Word("Book", "Kitap"),
            Word("Car", "Araba"),
            Word("Dog", "Köpek"),
            Word("House", "Ev"),
            Word("Table", "Masa"),
            Word("Chair", "Koltuk"),
            Word("Phone", "Telefon"),
            Word("Pen", "Kalem"),
            Word("Computer", "Bilgisayar"),
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
        return words.shuffled()  // Kelimeleri rastgele sırala
    }


    // Rastgele sıralama fonksiyonu
    fun shuffleWords() {
        _wordList.value = getShuffledWords()
    }
}
