package com.example.hillsloungeapplication.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface AlbumService {

    @GET("/albums")
    //@GET("/")
    suspend fun getAlbums():Response<Albums>

}