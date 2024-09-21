package com.mertyigit0.vocabcards.ui.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mertyigit0.vocabcards.data.local.WordDatabase
import com.mertyigit0.vocabcards.data.model.Category
import com.mertyigit0.vocabcards.data.repository.WordRepository
import kotlinx.coroutines.launch


class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WordRepository
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories
    private val _wordCount = MutableLiveData<Pair<Int, Int>>() // (öğrenilen kelime sayısı, toplam kelime sayısı)
    val wordCount: LiveData<Pair<Int, Int>> get() = _wordCount
    private val _wordCountMap = MutableLiveData<Map<Long, Pair<Int, Int>>>()
    val wordCountMap: LiveData<Map<Long, Pair<Int, Int>>> get() = _wordCountMap

    init {
        repository = WordRepository(application) // Yalnızca repository'i kullan
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            if (repository.getAllCategories().isEmpty()) { // Eğer kategoriler yoksa
                repository.loadCategoriesFromJson()
            }
            _categories.value = repository.getAllCategories()
        }
    }

    fun loadWordCountForCategory(categoryId: Long) {
        viewModelScope.launch {
            val totalWords = repository.getWordCountByCategory(categoryId)
            val learnedWords = repository.getLearnedWords().count { it.categoryId == categoryId }

            _wordCount.value = Pair(learnedWords, totalWords)
        }
    }

    fun loadWordCounts() {
        viewModelScope.launch {
            val counts = mutableMapOf<Long, Pair<Int, Int>>()
            val categories = repository.getAllCategories()

            for (category in categories) {
                val totalWords = repository.getWordCountByCategory(category.id)
                val learnedWords = repository.getLearnedWords().count { it.categoryId == category.id }
                counts[category.id] = Pair(learnedWords, totalWords)
            }
            _wordCountMap.value = counts
        }
    }



}

