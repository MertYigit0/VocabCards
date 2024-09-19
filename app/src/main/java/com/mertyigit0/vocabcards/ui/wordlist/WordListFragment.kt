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


class WordListFragment : Fragment() {

    private lateinit var viewModel: WordListViewModel
    private lateinit var adapter: WordAdapter
    private lateinit var binding: FragmentWordListBinding

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
        setHasOptionsMenu(true)  // Menü öğesini etkinleştir
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.word_list)

        viewModel = ViewModelProvider(this).get(WordListViewModel::class.java)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = WordAdapter(viewModel.wordList.value ?: emptyList()) { word ->
            val action = WordListFragmentDirections.actionWordListFragmentToWordDetailFragment(word)
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter

        viewModel.wordList.observe(viewLifecycleOwner) { wordList ->
            adapter.updateData(wordList)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.shuffleWords()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        // Uygulama başladığında listeyi güncelle
        viewModel.updateWordList()
    }



    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_word_list, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        // Arama sorgusu dinleyici
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Arama yapılınca tetiklenir
                query?.let {
                    viewModel.searchWord(it)  // Kelimeleri filtrele
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Arama metni değiştikçe tetiklenir
                newText?.let {
                    viewModel.searchWord(it)  // Kelimeleri filtrele
                }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("FragmentLifecycle", "onDestroyView: MyFragment")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FragmentLifecycle", "onDestroy: MyFragment")
    }

}

