package com.mistpaag.metaweatherapp.presentation.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material.Text
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mistpaag.domain.info.WLocationInfo
import com.mistpaag.imagemanager.loadAbbrImage
import com.mistpaag.metaweatherapp.R
import com.mistpaag.metaweatherapp.databinding.LocationDetailFragmentBinding
import com.mistpaag.metaweatherapp.parcelables.toDomain
import com.mistpaag.metaweatherapp.utils.formattedSunriseTime
import com.mistpaag.metaweatherapp.utils.formattedSunsetTime
import com.mistpaag.metaweatherapp.utils.formattedTime
import com.mistpaag.metaweatherapp.utils.toTemperatureText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        setupUI()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createTextComposable()
    }

    private fun createTextComposable() {
        binding.composeContainer.setContent {
            Text(text = "Hola desde compose")
        }
    }

    private fun createDetailImage(wLocationInfo: WLocationInfo){
        val current = wLocationInfo.consolidatedWeatherList.first()
        binding.composeContainer.setContent {
            ImageDescriptionCompose(
                imageURL = current.weatherStateAbbr,
                temp = getStringResource(R.string.temp_text, current.theTemp.toTemperatureText()),
                weatherStateName = current.weatherStateName
            )
        }
    }

    private fun setupUI(){
        binding.backToolbarIncluded.backImage.setOnClickListener { findNavController().popBackStack() }
        viewModel.state.observe(viewLifecycleOwner,{ state->
            when(state){
                is LocationDetailState.Error -> showMessage(state.message)
                is LocationDetailState.LocationInfo -> setLocationInfo(state.wLocationInfo)
            }
        })
    }

    private fun setLocationInfo(wLocationInfo: WLocationInfo) {
        setToolbarIncluded(wLocationInfo)
        setImageIncluded(wLocationInfo)
        setTimeIncluded(wLocationInfo)
        setItemsIncluded(wLocationInfo)
    }

    private fun setToolbarIncluded(wLocationInfo: WLocationInfo){
        binding.backToolbarIncluded.apply {
            titleText.text = wLocationInfo.title
        }
    }

    private fun setImageIncluded(wLocationInfo: WLocationInfo){
        createDetailImage(wLocationInfo = wLocationInfo)
        val current = wLocationInfo.consolidatedWeatherList.first()
        binding.detailImageIncluded.apply {
            tempImage.loadAbbrImage(current.weatherStateAbbr)
            tempText.text = getStringResource(R.string.temp_text, current.theTemp.toTemperatureText())
            tempDescText.text = current.weatherStateName
        }
    }

    private fun setTimeIncluded(wLocationInfo: WLocationInfo){
        binding.detailTimeIncluded.apply {
            timeText.text = wLocationInfo.formattedTime()
            sunsetText.text = wLocationInfo.formattedSunsetTime()
            sunriseText.text = wLocationInfo.formattedSunriseTime()
        }
    }

    private fun setItemsIncluded(wLocationInfo: WLocationInfo){
        val first = wLocationInfo.consolidatedWeatherList.first()
        binding.firstTempItem.apply {
            first.let {
                dateText.text = it.applicableDate
                minText.text = getStringResource(R.string.temp_text,it.minTemp.toTemperatureText())
                maxText.text = getStringResource(R.string.temp_text,it.maxTemp.toTemperatureText())
                iconImage.loadAbbrImage(it.weatherStateAbbr)
            }
        }

        val second = wLocationInfo.consolidatedWeatherList[1]
        binding.secondTempItem.apply {
            second.let {
                dateText.text = it.applicableDate
                minText.text = getStringResource(R.string.temp_text,it.minTemp.toTemperatureText())
                maxText.text = getStringResource(R.string.temp_text,it.maxTemp.toTemperatureText())
                iconImage.loadAbbrImage(it.weatherStateAbbr)
            }
        }

        val third = wLocationInfo.consolidatedWeatherList[1]
        binding.thirdTempItem.apply {
            third.let {
                dateText.text = it.applicableDate
                minText.text = getStringResource(R.string.temp_text,it.minTemp.toTemperatureText())
                maxText.text = getStringResource(R.string.temp_text,it.maxTemp.toTemperatureText())
                iconImage.loadAbbrImage(it.weatherStateAbbr)
            }
        }

        val fourth = wLocationInfo.consolidatedWeatherList[1]
        binding.fourthTempItem.apply {
            fourth.let {
                dateText.text = it.applicableDate
                minText.text = getStringResource(R.string.temp_text,it.minTemp.toTemperatureText())
                maxText.text = getStringResource(R.string.temp_text,it.maxTemp.toTemperatureText())
                iconImage.loadAbbrImage(it.weatherStateAbbr)
            }
        }
    }

    private fun showMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun getStringResource(idResource: Int, data: String = ""): String {
        return if (data.isNotEmpty()){
            requireContext().getString(idResource, data)
        }else{
            requireContext().getString(idResource)
        }
    }

    override fun onStart() {
        super.onStart()
        getArguments(arguments)
    }

    private fun getArguments(arguments: Bundle?) {
        arguments?.let {
            val wLocation = LocationDetailFragmentArgs.fromBundle(it).location.toDomain()
            viewModel.setIntentEvent(
                LocationDetailIntent.GetLocationInfo(wLocation)
            )
        }
    }
}