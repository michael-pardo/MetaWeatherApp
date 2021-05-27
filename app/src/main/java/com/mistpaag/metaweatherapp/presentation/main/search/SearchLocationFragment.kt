package com.mistpaag.metaweatherapp.presentation.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mistpaag.metaweatherapp.databinding.SearchLocationFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchLocationFragment : Fragment() {

    companion object {
        fun newInstance() = SearchLocationFragment()
    }

    private val viewModel by viewModels<SearchLocationViewModel>()
    private lateinit var binding: SearchLocationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchLocationFragmentBinding.inflate(inflater, container, false)

        setupUI()

        return binding.root
    }

    private fun setupUI(){
        setupSearchBar()
        val adapter = SearchLocationAdapter{

        }
        binding.searchRecycler.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner,{ state ->
            binding.progressBar.isVisible = state is SearchLocationState.Loading
            when (state) {
                is SearchLocationState.Error -> showMessage(state.message)
                SearchLocationState.Loading -> {}
                is SearchLocationState.SearchList -> adapter.submitList(state.list.toMutableList())
            }
        })
    }

    private fun setupSearchBar(){
        with(binding.searchBarIncluded){
            searchText.doOnTextChanged { text, _, _, _ ->
                viewModel.setIntentEvent(
                    SearchLocationIntent.SearchLocation(text.toString())
                )
            }
            closeImage.setOnClickListener {
                searchText.setText("")
            }
        }
    }

    private fun showMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }



}