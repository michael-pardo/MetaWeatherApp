package com.mistpaag.metaweatherapp.presentation.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mistpaag.metaweatherapp.R
import com.mistpaag.metaweatherapp.databinding.LocationDetailFragmentBinding
import com.mistpaag.metaweatherapp.parcelables.toDomain
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationDetailFragment : Fragment() {

    companion object {
        fun newInstance() = LocationDetailFragment()
    }

    private val viewModel by viewModels<LocationDetailViewModel>()
    private lateinit var binding: LocationDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LocationDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getArguments(arguments)
    }

    private fun getArguments(arguments: Bundle?) {
        arguments?.let {
            val wLocation = LocationDetailFragmentArgs.fromBundle(it).location.toDomain()
        }
    }
}