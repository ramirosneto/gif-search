package br.com.gifsearch.android.app.data.source.remote.repository

import br.com.gifsearch.android.app.BuildConfig
import br.com.gifsearch.android.app.data.source.remote.network.ApiRequest
import br.com.gifsearch.android.app.data.source.remote.network.GifsApi

class GifsRetrofitRepository(
    private val api: GifsApi
) : ApiRequest() {

    private val API_KEY = BuildConfig.API_KEY
    private val LIMIT = 51

    suspend fun trendingsGifs(offset: Int) = apiRequest {
        api.trendingsGifs(API_KEY, LIMIT, offset)
    }

    suspend fun searchGifs(offset: Int, query: String?) = apiRequest {
        api.searchGifs(API_KEY, LIMIT, offset, query)
    }
}