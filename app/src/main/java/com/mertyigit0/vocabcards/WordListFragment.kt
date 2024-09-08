package com.mertyigit0.vocabcards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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

        viewModel = ViewModelProvider(this).get(WordListViewModel::class.java)

        // RecyclerView ve SwipeRefreshLayout setup
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.wordList.observe(viewLifecycleOwner) { wordList ->
            adapter = WordAdapter(wordList)
            binding.recyclerView.adapter = adapter
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.shuffleWords()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}
