package com.mistpaag.requestmanager.base

import com.google.gson.Gson
import com.mistpaag.requestmanager.fakeSearchSuccessData
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection


internal class LocationDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/location/search?query=usa" -> {
                val gson = Gson()
                val json = gson.toJson(fakeSearchSuccessData)
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(json)
            }
            else -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody("[]")
            }
        }
    }

}



