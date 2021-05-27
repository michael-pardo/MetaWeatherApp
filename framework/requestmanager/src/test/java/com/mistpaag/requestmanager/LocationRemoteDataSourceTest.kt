package com.mistpaag.requestmanager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mistpaag.commons_testing.CoroutineTestRule
import com.mistpaag.data.ResultsData
import com.mistpaag.requestmanager.base.BaseTest
import com.mistpaag.requestmanager.mapper.toDomain
import com.mistpaag.requestmanager.model.WLocationServer
import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

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
}

val fakeSearchQuery = "usa"
val fakeResponseList = listOf(
    WLocationServer(title = "Houston", locationType = "City", woeid = 2424766, lattLong = "29.760450,-95.369781"),
    WLocationServer(title = "Austin", locationType = "City", woeid = 2357536, lattLong = "30.267599,-97.742981"),
    WLocationServer(title = "Columbus", locationType = "City", woeid = 2383660, lattLong = "39.961990,-83.002747"),
    WLocationServer(title = "Busan", locationType = "City", woeid = 1132447, lattLong = "35.170429,128.999481"),
)
val fakeSearchSuccessData = fakeResponseList.filter { it.title.contains("usa") }