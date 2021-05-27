package com.mistpaag.requestmanager

import com.mistpaag.data.ResultsData
import retrofit2.Response
import java.io.IOException

suspend fun <T : Any> safeApiCall(
    call: suspend () -> ResultsData<T>,
    errorMessage: String
): ResultsData<T> = try {
    call.invoke()
} catch (e: Exception) {
    ResultsData.Error("$errorMessage ${e.localizedMessage}")
}

fun <T : Any> Response<T>.callServices(): ResultsData<T> {
    if (this.isSuccessful) {
        return if (this.body() != null) {
            ResultsData.Success(this.body() as T)
        } else{
            ResultsData.Error(this.errorBody().toString())
        }
    }
    return ResultsData.Error(IOException().toString())
}