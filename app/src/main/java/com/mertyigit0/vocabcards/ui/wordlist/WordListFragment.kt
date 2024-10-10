package com.mertyigit0.vocabcards.ui.wordlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.databinding.FragmentWordListBinding
import com.mertyigit0.vocabcards.ui.category.SharedViewModel


class WordListFragment : Fragment() {

    private lateinit var viewModel: WordListViewModel
    private lateinit var adapter: WordAdapter
    private lateinit var binding: FragmentWordListBinding
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FragmentLifecycle", "onCreate: MyFragment")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordListBinding.inflate(inflater, container, false)
        Log.d("FragmentLifecycle", "onCreateView: MyFragment")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FragmentLifecycle", "onViewCreated: MyFragment")
        setHasOptionsMenu(true)
        setupActionBar()
        setupViewModels()
        setupRecyclerView()
        setupSwipeToRefresh()

        observeWordList()
    }

    // AppBar'ın başlığını ayarlayan metod
    private fun setupActionBar() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.word_list)
    }

    // ViewModel'leri ayarlayan metod
    private fun setupViewModels() {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        viewModel = ViewModelProvider(this)[WordListViewModel::class.java]


        sharedViewModel.categoryId?.let { categoryId ->
            viewModel.setCategoryId(categoryId)
        }

    }

    // RecyclerView ayarlarını ve adapter'ı ayarlayan metod
    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = WordAdapter(viewModel.wordList.value ?: emptyList()) { word ->
            val action = WordListFragmentDirections.actionWordListFragmentToWordDetailFragment(word)
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
    }

    // Swipe to refresh işlevini ayarlayan metod
    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.shuffleWords()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }



    // WordList değişikliklerini gözlemleyen metod
    private fun observeWordList() {
        viewModel.wordList.observe(viewLifecycleOwner) { wordList ->
            adapter.updateData(wordList)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_word_list, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView


        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                query?.let {
                    viewModel.searchWord(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                newText?.let {
                    viewModel.searchWord(it)
                }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }


}

