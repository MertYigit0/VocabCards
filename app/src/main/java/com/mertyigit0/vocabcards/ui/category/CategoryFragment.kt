package com.mertyigit0.vocabcards.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.data.model.Category
import com.mertyigit0.vocabcards.data.repository.WordRepository
import com.mertyigit0.vocabcards.ui.wordlist.WordListFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CategoryFragment : Fragment(), OnCategoryClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.categoryRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        categoryViewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoryAdapter = CategoryAdapter(categories, this) // Dinleyiciyi ekle
            recyclerView.adapter = categoryAdapter
        }
    }

    override fun onCategoryClick(categoryId: Long) {
        sharedViewModel.categoryId = categoryId
        findNavController().navigate(R.id.action_categoryFragment_to_wordListFragment)
    }
}

