package com.mistpaag.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mistpaag.commons_testing.CoroutineTestRule
import com.mistpaag.data.LocationRepository
import com.mistpaag.data.ResultsData
import com.mistpaag.domain.info.ConsolidatedWeather
import com.mistpaag.domain.info.WLocationInfo
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
class GetLocationInfoUseCaseTest{
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var getLocationInfoUseCase: GetLocationInfoUseCase

    @RelaxedMockK
    lateinit var repository: LocationRepository

    @Before
    fun setup(){
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        getLocationInfoUseCase = GetLocationInfoUseCase(repository)
    }

    @Test
    fun `get location info when invoke getLocationInfo `() = coroutineTestRule.testDispatcher.runBlockingTest {
        every { repository.getLocationInfo(fakeWoeid) } returns fakeLocationInfoFlow

        val result = getLocationInfoUseCase.invoke(fakeWoeid)

        verify(exactly = 1) { repository.getLocationInfo(any()) }

        result.collect {
            Assert.assertEquals((it as ResultsData.Success).data, fakeLocationInfo)
        }
    }

    @Test
    fun `get error when invoke getLocationInfo`() = coroutineTestRule.testDispatcher.runBlockingTest {
        every { repository.getLocationInfo(fakeWoeidError) } returns fakeLocationErrorFlow

        val result = getLocationInfoUseCase.invoke(-1L)

        result.collect {
            Assert.assertEquals(it, ResultsData.Error("Error"))
        }
    }
}

val fakeWoeid = 1132447L
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
val fakeLocationErrorFlow = flow {
    emit(
        ResultsData.Error(
            "Error"
        )
    )
}