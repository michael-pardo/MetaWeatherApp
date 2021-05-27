package com.mistpaag.metaweatherapp.main.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mistpaag.metaweatherapp.R
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
        return binding.root
    }


}