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




}

