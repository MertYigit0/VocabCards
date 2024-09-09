package com.mertyigit0.vocabcards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mertyigit0.vocabcards.databinding.FragmentLearnedListBinding


class LearnedListFragment : Fragment() {

    private lateinit var binding: FragmentLearnedListBinding
    private lateinit var adapter: WordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLearnedListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = WordAdapter(getLearnedWords()) { word ->
            val action = LearnedListFragmentDirections.actionLearnedListFragmentToWordDetailFragment(word)
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun getLearnedWords(): List<Word> {
        val learnedWords = PrefsHelper.getLearnedWords(requireContext())
        // Convert learnedWords to Word objects, adjust according to your data source
        // Example code:
        return learnedWords.map { Word(it, "Translation") } // Adjust translation accordingly
    }

    override fun onResume() {
        super.onResume()
        adapter.updateData(getLearnedWords())
    }
}
