package com.example.hillsloungeapplication

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class YandexApi:Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("40bbbc8a-4406-4efc-8f73-c782d465d1d2")
    }
}