package com.mertyigit0.vocabcards.ui.learnedlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mertyigit0.vocabcards.R
import com.mertyigit0.vocabcards.data.model.Word
import com.mertyigit0.vocabcards.ui.wordlist.WordAdapter
import com.mertyigit0.vocabcards.databinding.FragmentLearnedListBinding


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

        setHasOptionsMenu(true)
        setActionBarTitle()

        initializeViewModel()
        setupRecyclerView()
        observeViewModelData()

        // Verileri güncellemek için
        viewModel.updateLearnedWords()
    }

    // ActionBar başlığını ayarlayan fonksiyon
    private fun setActionBarTitle() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.learned_words)
    }

    // ViewModel'i initialize eden fonksiyon
    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this)[LearnedListViewModel::class.java]
    }

    // RecyclerView'i ayarlayan fonksiyon
    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = WordAdapter(emptyList()) { word ->
            navigateToWordDetailFragment(word)
        }
        binding.recyclerView.adapter = adapter
    }

    // ViewModel'deki verileri gözlemleyen fonksiyon
    private fun observeViewModelData() {
        viewModel.learnedWords.observe(viewLifecycleOwner) { words ->
            adapter.updateData(words)
        }

        viewModel.noWordsMessage.observe(viewLifecycleOwner) { message ->
            handleNoWordsMessage(message)
        }
    }

    // Kelime detaylarına gitmek için navigation işlemi
    private fun navigateToWordDetailFragment(word: Word) {
        val action = LearnedListFragmentDirections.actionLearnedListFragmentToWordDetailFragment(word)
        findNavController().navigate(action)
    }

    // Kelime listesi boş olduğunda mesaj gösteren fonksiyon
    private fun handleNoWordsMessage(message: String?) {
        val tvNoWords = view?.findViewById<TextView>(R.id.tvNoWords)
        if (message != null) {
            tvNoWords?.text = message
            tvNoWords?.visibility = View.VISIBLE
        } else {
            tvNoWords?.visibility = View.GONE
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
                    viewModel.searchLearnedWord(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                newText?.let {
                    viewModel.searchLearnedWord(it)
                }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}
