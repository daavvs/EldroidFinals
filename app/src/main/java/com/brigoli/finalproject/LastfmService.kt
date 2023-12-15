package com.brigoli.finalproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LastfmService {
    @GET("?method=chart.gettoptracks")
    fun getTopTracks(
        @Query("api_key") apiKey: String = "2f54eb9d81fcae1d54b42dca0d07cf78",
        @Query("format") format: String
    ): Call<LastfmResponse>

    @GET("?method=track.search")
    fun searchTracks(
        @Query("api_key") apiKey: String = "2f54eb9d81fcae1d54b42dca0d07cf78",
        @Query("format") format: String,
        @Query("track") track: String
    ): Call<LastfmResponse>
}