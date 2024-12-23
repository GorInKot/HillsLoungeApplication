package com.example.hillsloungeapplication.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroftInstance {

    companion object {
        //val mainUrl = "https://jsonplaceholder.typicode.com"
        val mainUrl = "https://0.0.0.0:1234"

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(mainUrl)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }
    }
}