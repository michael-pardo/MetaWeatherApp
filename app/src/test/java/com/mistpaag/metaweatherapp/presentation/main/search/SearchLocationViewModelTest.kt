package com.mistpaag.metaweatherapp.presentation.main.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mistpaag.commons_testing.CoroutineTestRule
import com.mistpaag.data.ResultsData
import com.mistpaag.domain.WLocation
import com.mistpaag.usecases.SearchLocationUseCase
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
class SearchLocationViewModelTest{
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: SearchLocationViewModel

    @RelaxedMockK
    lateinit var searchLocationUseCase: SearchLocationUseCase

    @RelaxedMockK
    lateinit var observable: Observer<SearchLocationState>

    var slot = slot<SearchLocationState>()
    var mutableList = mutableListOf<SearchLocationState>()

    @Before
    fun setup(){
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        viewModel = SearchLocationViewModel(
            searchLocationUseCase
        ).apply { state.observeForever(observable) }
        every { observable.onChanged(capture(slot)) } answers {mutableList.add(slot.captured) }
    }

    @Test
    fun `get list when invoke LocationSearchViewModel received search location intent`() = coroutineTestRule.testDispatcher.runBlockingTest {
        every { searchLocationUseCase.invoke(fakeSearchLocationName) } returns fakeLocationSearchNameListFlow

        val event = SearchLocationIntent.SearchLocation("usa")

        viewModel.setIntentEvent(event)

        verify(exactly = 1) { searchLocationUseCase.invoke(any()) }
        Assert.assertEquals(2, mutableList.size)
        Assert.assertEquals((slot.captured as SearchLocationState.SearchList).list.first(), fakeLocationNameSearchList.first())
    }

    @Test
    fun `get error when invoke LocationSearchViewModel received search location intent`() = coroutineTestRule.testDispatcher.runBlockingTest {
        every { searchLocationUseCase.invoke(fakeSearchLocationError) } returns fakeLocationNameSearchListErrorFlow

        val event = SearchLocationIntent.SearchLocation("error")

        viewModel.setIntentEvent(event)

        verify(exactly = 1) { searchLocationUseCase.invoke(any()) }
        Assert.assertEquals(2, mutableList.size)
        Assert.assertEquals((slot.captured as SearchLocationState.Error).message, "Error on search")
    }

    @Test
    fun `verify that LocationSearchViewModel doesn't invoke searchLocationUseCase when query is empty`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val event = SearchLocationIntent.SearchLocation("")

        viewModel.setIntentEvent(event)

        verify(exactly = 0) { searchLocationUseCase.invoke(any()) }
        Assert.assertEquals(0, mutableList.size)
    }
}

val fakeSearchLocationName = "usa"
val fakeLocationList = listOf(
    WLocation(title = "Houston", locationType = "City", woeid = 2424766),
    WLocation(title = "Austin", locationType = "City", woeid = 2357536),
    WLocation(title = "Columbus", locationType = "City", woeid = 2383660),
    WLocation(title = "Busan", locationType = "City", woeid = 1132447),
)
val fakeLocationNameSearchList = fakeLocationList.filter { it.title.contains("usa") }
val fakeLocationSearchNameListFlow = flow {
    emit(
        ResultsData.Success(
            fakeLocationNameSearchList
        )
    )
}

val fakeSearchLocationError = "error"
val fakeLocationNameSearchListErrorFlow = flow {
    emit(
        ResultsData.Error(
            "Error on search"
        )
    )
}