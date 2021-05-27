package com.mistpaag.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mistpaag.commons_testing.CoroutineTestRule
import com.mistpaag.data.LocationRepository
import com.mistpaag.data.ResultsData
import com.mistpaag.domain.WLocation
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchLocationUseCaseTest{
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var searchLocationUseCase: SearchLocationUseCase

    @RelaxedMockK
    lateinit var repository: LocationRepository

    @Before
    fun setup(){
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        searchLocationUseCase = SearchLocationUseCase(repository)
    }

    @Test
    fun `get list when invoke searchLocationByQuery `() = coroutineTestRule.testDispatcher.runBlockingTest {
        every { repository.searchLocationByQuery(fakeLocationQueryName) } returns fakeLocationQueryFlow

        val result = searchLocationUseCase.invoke("usa")

        verify(exactly = 1) { repository.searchLocationByQuery(any()) }

        result.collect {
            Assert.assertEquals((it as ResultsData.Success).data.first(), fakeLocationQuerySearchList.first())
        }
    }

    @Test
    fun `get error when invoke searchLocationByQuery`() = coroutineTestRule.testDispatcher.runBlockingTest {
        every { repository.searchLocationByQuery(fakeLocationQueryErrorName) } returns fakeLocationSearchQueryListErrorFlow

        val result = searchLocationUseCase.invoke(fakeLocationQueryErrorName)

        result.collect {
            Assert.assertEquals(it, ResultsData.Error("Error on search"))
        }
    }
}

val fakeLocationQueryName = "usa"
val fakeLocationQueryErrorName = "error"
val fakeLocationSearchQueryListErrorFlow = flow {
    emit(
        ResultsData.Error(
            "Error on search"
        )
    )
}
val fakeLocationQueryList = listOf(
    WLocation(title = "Houston", locationType = "City", woeid = 2424766),
    WLocation(title = "Austin", locationType = "City", woeid = 2357536),
    WLocation(title = "Columbus", locationType = "City", woeid = 2383660),
    WLocation(title = "Busan", locationType = "City", woeid = 1132447),
)
val fakeLocationQuerySearchList = fakeLocationQueryList.filter { it.title.contains("usa") }
val fakeLocationQueryFlow = flow {
    emit(
        ResultsData.Success(
            fakeLocationQueryList.filter { it.title.contains("usa")}
        )
    )
}