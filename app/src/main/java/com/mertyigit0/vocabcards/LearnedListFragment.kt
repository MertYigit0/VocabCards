package com.mertyigit0.vocabcards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
        (activity as AppCompatActivity).supportActionBar?.title = "Learned Words"

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
}
