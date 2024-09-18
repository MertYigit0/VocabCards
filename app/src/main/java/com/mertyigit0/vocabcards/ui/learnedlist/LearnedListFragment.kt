package com.mertyigit0.vocabcards.ui.learnedlist

import android.os.Bundle
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
import com.mertyigit0.vocabcards.ui.wordlist.WordAdapter
import com.mertyigit0.vocabcards.databinding.FragmentLearnedListBinding
import com.mertyigit0.vocabcards.ui.learnedlist.LearnedListFragmentDirections

class LearnedListFragment : Fragment() {

    private lateinit var binding: FragmentLearnedListBinding
    private lateinit var adapter: WordAdapter
    private lateinit var viewModel: LearnedListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLearnedListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)  // Menü öğesini etkinleştir
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.learned_words)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(LearnedListViewModel::class.java)

        // Initialize RecyclerView
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = WordAdapter(emptyList()) { word ->
            val action = LearnedListFragmentDirections.actionLearnedListFragmentToWordDetailFragment(word)
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter

        // Observe data changes
        viewModel.learnedWords.observe(viewLifecycleOwner) { words ->
            adapter.updateData(words)
        }

        // Update data when fragment resumes
        viewModel.updateLearnedWords()
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
                    viewModel.searchLearnedWord(it)  // Kelimeleri filtrele
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Arama metni değiştikçe tetiklenir
                newText?.let {
                    viewModel.searchLearnedWord(it)   // Kelimeleri filtrele
                }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}
