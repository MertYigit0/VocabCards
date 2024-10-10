package com.mertyigit0.vocabcards.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.ui.wordlist.WordListViewModel


class CategoryFragment : Fragment(), OnCategoryClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModels()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()
        setupRecyclerView(view)
        observeCategoryData()
        observeWordCounts()
    }

    // ViewModel'leri initialize eden fonksiyon
    private fun initializeViewModels() {
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        // Kelimelerin uygulama ilk yüklendiğinde JSON'dan çekilmesi için
        val wordListViewModel = ViewModelProvider(this)[WordListViewModel::class.java]
    }

    // ActionBar başlığını ayarlayan fonksiyon
    private fun setupActionBar() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.categories)
    }

    // RecyclerView'i kuran fonksiyon
    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.categoryRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    // Kategorileri gözlemleyen fonksiyon
    private fun observeCategoryData() {
        categoryViewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoryAdapter = CategoryAdapter(categories, this)
            recyclerView.adapter = categoryAdapter

            // Kelime sayılarını yükle
            categoryViewModel.loadWordCounts()
        }
    }

    // Kelime sayılarını gözlemleyen fonksiyon
    private fun observeWordCounts() {
        categoryViewModel.wordCountMap.observe(viewLifecycleOwner) { wordCounts ->
            categoryAdapter.updateWordCounts(wordCounts) // Kelime sayılarını güncelle
        }
    }

    // Kategori tıklamasını işleyen fonksiyon
    override fun onCategoryClick(categoryId: Int) {
        sharedViewModel.categoryId = categoryId
        findNavController().navigate(R.id.action_categoryFragment_to_wordListFragment)
    }
}


