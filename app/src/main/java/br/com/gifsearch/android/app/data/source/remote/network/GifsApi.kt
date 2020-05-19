package br.com.gifsearch.android.app.data.source.remote.network

import br.com.gifsearch.android.app.BuildConfig
import br.com.gifsearch.android.app.data.model.GifResponseDTO
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GifsApi {

    @GET("trending")
    suspend fun trendingsGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<GifResponseDTO>

    @GET("search")
    suspend fun searchGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("q") query: String? = null
    ): Response<GifResponseDTO>

    companion object {
        operator fun invoke(): GifsApi {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build()
                .create(GifsApi::class.java)
        }
    }
}