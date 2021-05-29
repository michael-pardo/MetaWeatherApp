package com.mistpaag.requestmanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mistpaag.commons_testing.CoroutineTestRule
import com.mistpaag.data.ResultsData
import com.mistpaag.requestmanager.base.BaseTest
import com.mistpaag.requestmanager.mapper.info.toDomain
import com.mistpaag.requestmanager.mapper.toDomain
import com.mistpaag.requestmanager.model.WLocationServer
import com.mistpaag.requestmanager.model.info.ConsolidatedWeatherServer
import com.mistpaag.requestmanager.model.info.ParentServer
import com.mistpaag.requestmanager.model.info.WLocationInfoServer
import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class LocationRemoteDataSourceTest: BaseTest() {
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var remote: LocationRemoteDataSource

    @Before
    override fun setup(){
        super.setup()
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        remote = LocationRemoteDataSource(locationService = locationService)
    }

    @Test
    fun `get location list from search location endpoint`() = runBlocking{

        val response = remote.searchLocationByQuery(fakeSearchQuery)

        response.collect {
            assertEquals(it, ResultsData.Success(fakeSearchSuccessData.toDomain()) )
        }
    }

    @Test
    fun `get location info from get location info endpoint`() = runBlocking{

        val response = remote.getLocationInfo(fakeWoeid)

        response.collect {
            assertEquals(it, ResultsData.Success(fakeLocationInfoSuccess.toDomain()) )
        }
    }
}

val fakeSearchQuery = "usa"
val fakeResponseList = listOf(
    WLocationServer(title = "Houston", locationType = "City", woeid = 2424766, lattLong = "29.760450,-95.369781"),
    WLocationServer(title = "Austin", locationType = "City", woeid = 2357536, lattLong = "30.267599,-97.742981"),
    WLocationServer(title = "Columbus", locationType = "City", woeid = 2383660, lattLong = "39.961990,-83.002747"),
    WLocationServer(title = "Busan", locationType = "City", woeid = 1132447, lattLong = "35.170429,128.999481"),
)
val fakeSearchSuccessData = fakeResponseList.filter { it.title.contains("usa") }


val fakeWoeid = 1132447L
val fakeConsolidatedWeatherList = listOf(
    ConsolidatedWeatherServer(
        airPressure=0.2,
        applicableDate="Today",
        created="2021",
        humidity=12,
        id=5050778780696576,
        minTemp=14.96,
        maxTemp=23.265,
        predictability=0,
        theTemp=27.0,
        visibility=1.0,
        weatherStateAbbr="c",
        weatherStateName="Clear",
        windDirection = 126.5000,
        windDirectionCompass="SE",
        windSpeed= 3.96,
    ),
    ConsolidatedWeatherServer(
        airPressure=998.0,
        applicableDate="Yesterday",
        created="2021",
        humidity=14,
        id=6044250216071168,
        minTemp=13.96,
        maxTemp=25.265,
        predictability=0,
        theTemp=24.0,
        visibility=1.0,
        weatherStateAbbr="c",
        weatherStateName="Clear",
        windDirection = 126.5000,
        windDirectionCompass="SE",
        windSpeed= 3.96,
    ),
)
val fakeParentServer = ParentServer(
    lattLong="36.448151,127.850166",
    locationType="Country",
    title="South Korea",
    woeid=23424868,
)
val fakeSuccessLocationInfo = WLocationInfoServer(
    consolidatedWeatherServerList=fakeConsolidatedWeatherList,
    lattLong="35.170429,128.999481",
    locationType="City",
    parentServer= fakeParentServer,
    sourceServers=emptyList(),
    sunRise="2021-05-29T05:11:37.264071+09:00",
    sunSet="2021-05-29T19:31:07.339587+09:00",
    time="2021-05-29",
    timezone="Asia/Seoul",
    timezoneName="LMT",
    title="Busan",
    woeid=1132447L,
)
val fakeLocationInfoSuccess = fakeSuccessLocationInfo