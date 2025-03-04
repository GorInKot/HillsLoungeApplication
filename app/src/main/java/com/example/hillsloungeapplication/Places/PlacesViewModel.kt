package com.example.hillsloungeapplication.Places

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hillsloungeapplication.Places.Details.PlaceDetailFragment
import com.example.hillsloungeapplication.R

class PlacesViewModel : ViewModel() {
    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>> = _places

    init {
        _places.value = listOf(
            Place(
                "Hills Lounge | Саларьево",
                "Россия, Москва, ул. Саларьевская, д. 16, к.3",
                R.drawable.hills_sal_new,
                "Мы работаем до 01:00!",
                55.617211635322,
                37.4087668112054,
                "https://t.me/s1_Dan",
                "+79154689314",
                "https://hills-lounge.clients.site"
            ),

            Place(
                "Hills Lounge | Румянцево",
                "Россия, Москва, ул. Родниковая, д. 9А, к.2",
                R.drawable.hills_rumyantsevo,
                "Мы работаем до 01:00!",
                55.63390917549483,
                37.41457944422558,
                "https://t.me/s1_Dan",
                "+79153243353",
                "https://hills-lounge-1694027914.clients.site"
            ),

            Place(
                "Hills Lounge | Солнцево",
                "Россия, Москва, ул. Юлиана Семенова, д. 8, к.1",
                R.drawable.hills_sol_new,
                "Мы работаем до 01:00!",
                55.64359602205373,
                37.393294931877165,
                "https://t.me/s1_Dan",
                "+79854923833",
                null
            )
        )
    }

    fun getPlaceDetails(selectedPlace: Place): PlaceDetailFragment {
        return PlaceDetailFragment.newInstance(
            selectedPlace.title,
            selectedPlace.timeDescription,
            selectedPlace.imageView,
            selectedPlace.address,
            "Понедельник:   12:00 - 01:00\n" +
                    "Вторник:   12:00 - 01:00\n" +
                    "Среда:   12:00 - 01:00\n" +
                    "Четверг:   12:00 - 01:00\n" +
                    "Пятница:   12:00 - 02:00\n" +
                    "Суббота:   12:00 - 02:00\n" +
                    "Воскресенье:   12:00 - 01:00\n",
            selectedPlace.latitude,
            selectedPlace.longitude,
            selectedPlace.telegramLink,
            selectedPlace.phoneNumber,
            selectedPlace.menu ?: ""
        )
    }
}

