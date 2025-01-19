package com.example.hillsloungeapplication.Places

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hillsloungeapplication.R

class PlacesViewModel: ViewModel() {

    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>> = _places

//    private val _currentIndex = MutableLiveData(0)
//    val currentIndex: LiveData<Int> = _currentIndex

    init {
        _places.value = listOf(
            Place("Hills Lounge | Саларьево",
                "Россия, Москва, ул. Саларьевская, д. 16, к.3",
                (R.drawable.hills_sal),
                "Мы работаем до 01:00!"),

            Place("Hills Lounge | Румянцево",
                "Россия, Москва, ул. Родниковая, д. 9А, к.2",
                R.drawable.hills_rum,
                "Мы работаем до 01:00!"),

            Place("Hills Lounge | Солнцево",
                "Россия, Москва, ул. Юлиана Семенова, д. 8, к.1",
                R.drawable.hills_sol,
                "Мы работаем до 01:00!")
        )
    }
}