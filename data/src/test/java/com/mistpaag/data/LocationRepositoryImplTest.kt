package com.mistpaag.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mistpaag.commons_testing.CoroutineTestRule
import com.mistpaag.data.source.LocationRemoteSource
import com.mistpaag.domain.WLocation
import com.mistpaag.domain.info.ConsolidatedWeather
import com.mistpaag.domain.info.WLocationInfo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LocationRepositoryImplTest{
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: LocationRepositoryImpl

    @RelaxedMockK
    lateinit var remoteSource: LocationRemoteSource

    @Before
    fun setup(){
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        repository = LocationRepositoryImpl(remoteSource)
    }

    @Test
    fun `get list when invoke searchLocationByQuery from remote source `() = coroutineTestRule.testDispatcher.runBlockingTest {
        coEvery { remoteSource.searchLocationByQuery(fakeLocationQueryName) } returns fakeLocationQueryFlow

        val result = repository.searchLocationByQuery("usa")

        result.collect {
            coVerify (exactly = 1) {
                remoteSource.searchLocationByQuery(any())
            }
            Assert.assertEquals((it as ResultsData.Success).data[0], fakeLocationQuerySearchList.first().copy())
        }
    }

    @Test
    fun `get error when invoke searchLocationByQuery from remote source `() = coroutineTestRule.testDispatcher.runBlockingTest {
        coEvery { remoteSource.searchLocationByQuery(fakeLocationQueryErrorName) } returns fakeLocationSearchQueryListErrorFlow

        val result = repository.searchLocationByQuery(fakeLocationQueryErrorName)

        result.collect {
            coVerify (exactly = 1) {
                remoteSource.searchLocationByQuery(any())
            }
            Assert.assertEquals((it as ResultsData.Error), ResultsData.Error("Error on search"))
        }
    }

    @Test
    fun `get location when invoke getLocationInfo from remote source `() = coroutineTestRule.testDispatcher.runBlockingTest {
        coEvery { remoteSource.getLocationInfo(fakeWoeid) } returns fakeLocationInfoFlow

        val result = repository.getLocationInfo(fakeWoeid)

        result.collect {
            coVerify (exactly = 1) {
                remoteSource.getLocationInfo(any())
            }
            Assert.assertEquals((it as ResultsData.Success).data, fakeLocationInfo.copy())
        }
    }

    @Test
    fun `get error when invoke getLocationInfo from remote source `() = coroutineTestRule.testDispatcher.runBlockingTest {
        coEvery { remoteSource.getLocationInfo(fakeWoeidError) } returns fakeLocationErrorFlow

        val result = repository.getLocationInfo(-1L)

        result.collect {
            coVerify (exactly = 1) {
                remoteSource.getLocationInfo(any())
            }
            Assert.assertEquals((it as ResultsData.Error), ResultsData.Error("Error"))
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