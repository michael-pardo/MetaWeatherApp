package com.mistpaag.metaweatherapp.presentation.main.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mistpaag.commons_testing.CoroutineTestRule
import com.mistpaag.data.ResultsData
import com.mistpaag.domain.WLocation
import com.mistpaag.domain.info.ConsolidatedWeather
import com.mistpaag.domain.info.WLocationInfo
import com.mistpaag.usecases.GetLocationInfoUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LocationDetailViewModelTest{
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: LocationDetailViewModel

    @RelaxedMockK
    lateinit var getLocationInfoUseCase: GetLocationInfoUseCase

    @RelaxedMockK
    lateinit var observable: Observer<LocationDetailState>

    var slot = slot<LocationDetailState>()
    var mutableList = mutableListOf<LocationDetailState>()

    @Before
    fun setup(){
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        viewModel = LocationDetailViewModel(
            getLocationInfoUseCase
        ).apply { state.observeForever(observable) }
        every { observable.onChanged(capture(slot)) } answers {mutableList.add(slot.captured) }
    }

    @Test
    fun `get location info when invoke LocationDetailViewModel received get location info intent`() = coroutineTestRule.testDispatcher.runBlockingTest {
        every { getLocationInfoUseCase.invoke(fakeWoeid) } returns fakeLocationInfoFlow

        val event = LocationDetailIntent.GetLocationInfo(fakewLocation)

        viewModel.setIntentEvent(event)

        verify(exactly = 1) { getLocationInfoUseCase.invoke(any()) }
        Assert.assertEquals(1, mutableList.size)
        Assert.assertEquals((slot.captured as LocationDetailState.LocationInfo).wLocationInfo, fakeLocationInfo)
    }

    @Test
    fun `get error when invoke LocationDetailViewModel received get location info intent`() = coroutineTestRule.testDispatcher.runBlockingTest {
        every { getLocationInfoUseCase.invoke(fakeWoeidError) } returns fakeLocationErrorFlow

        val event = LocationDetailIntent.GetLocationInfo(fakeLocationError)

        viewModel.setIntentEvent(event)

        verify(exactly = 1) { getLocationInfoUseCase.invoke(any()) }
        Assert.assertEquals(1, mutableList.size)
        Assert.assertEquals((slot.captured as LocationDetailState.Error).message, "Error")
    }
}

val fakeWoeid = 1132447L
val fakewLocation = WLocation(title = "Busan", locationType = "City", woeid = 1132447)
val fakeConsolidatedWeatherList = listOf(
    ConsolidatedWeather(
        id=5050778780696576,
        weatherStateName="Clear",
        applicableDate="Today",
        minTemp=14.96,
        maxTemp=23.265,
        weatherStateAbbr="c",
    ),
    ConsolidatedWeather(
        id=5185503314837504,
        weatherStateName="Clear",
        applicableDate="tomorrow",
        minTemp=14.96,
        maxTemp=23.265,
        weatherStateAbbr="c",
    ),
)
val fakeLocationInfo = WLocationInfo(
    title="Busan",
    locationType="City",
    woeid=1132447L,
    time="2021-05-29",
    sunRise="2021-05-29T05:11:37.264071+09:00",
    sunSet="2021-05-29T19:31:07.339587+09:00",
    consolidatedWeatherList=fakeConsolidatedWeatherList,
)

val fakeLocationInfoFlow = flow {
    emit(
        ResultsData.Success(
            fakeLocationInfo
        )
    )
}
val fakeWoeidError = -1L
val fakeLocationError = WLocation(title = "Error", locationType = "Error", woeid = fakeWoeidError)
val fakeLocationErrorFlow = flow {
    emit(
        ResultsData.Error(
            "Error"
        )
    )
}