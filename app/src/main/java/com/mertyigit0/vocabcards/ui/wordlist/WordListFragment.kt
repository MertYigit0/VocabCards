package com.mertyigit0.vocabcards.ui.wordlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mertyigit0.vocabcards.databinding.FragmentWordListBinding


class WordListFragment : Fragment() {

    private lateinit var viewModel: WordListViewModel
    private lateinit var adapter: WordAdapter
    private lateinit var binding: FragmentWordListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Word List"

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
}

