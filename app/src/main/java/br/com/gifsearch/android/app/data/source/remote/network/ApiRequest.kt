package br.com.gifsearch.android.app.data.source.remote.network

import retrofit2.Response
import java.io.IOException

abstract class ApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): Any {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw ApiException(response.message())
        }
    }
}

class ApiException(message: String) : IOException(message)