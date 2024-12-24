package com.example.hillsloungeapplication.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface AlbumService {

//    @GET("/albums")
    @GET("/test_message")
    suspend fun getAlbums():Response<Albums>

}