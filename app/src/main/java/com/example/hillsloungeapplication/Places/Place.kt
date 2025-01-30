package com.example.hillsloungeapplication.Places

data class Place(
    val title: String,
    val address: String,
    val imageView: Int,
    val timeDescription: String,
    val latitude: Double,
    val longitude: Double,
    val telegramLink: String,
    val phoneNumber: String,
    val menu: String?
)