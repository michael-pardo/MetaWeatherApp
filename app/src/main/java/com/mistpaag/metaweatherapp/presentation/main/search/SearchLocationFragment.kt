package com.mistpaag.metaweatherapp.presentation.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mistpaag.domain.WLocation
import com.mistpaag.metaweatherapp.databinding.SearchLocationFragmentBinding
import com.mistpaag.metaweatherapp.parcelables.toParcelable
import com.mistpaag.metaweatherapp.utils.hideKeyboard
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
            goToDetail(it)
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
            searchText.apply {
                doOnTextChanged { text, _, _, _ ->
                    viewModel.setIntentEvent(
                        SearchLocationIntent.SearchLocation(text.toString())
                    )
                }
                setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        searchText.hideKeyboard()
                        return@OnEditorActionListener true
                    }
                    return@OnEditorActionListener false
                })
            }

            closeImage.setOnClickListener {
                searchText.setText("")
            }
        }
    }

    private fun showMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun goToDetail(wLocation: WLocation){
        findNavController().navigate(
            SearchLocationFragmentDirections.actionSearchLocationFragmentToLocationDetailFragment(
                wLocation.toParcelable()
            )
        )
    }


}