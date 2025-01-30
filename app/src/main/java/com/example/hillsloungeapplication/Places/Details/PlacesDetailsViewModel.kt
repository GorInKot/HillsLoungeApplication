package com.example.hillsloungeapplication.Places.Details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hillsloungeapplication.Places.Details.RV.ServicesCard
import com.example.hillsloungeapplication.R

class PlacesDetailsViewModel(application: Application): AndroidViewModel(application) {

    private val _cards = MutableLiveData<List<ServicesCard>>()
    val cards: LiveData<List<ServicesCard>> = _cards

    init {
        _cards.value = listOf(
            ServicesCard(
                "Вечеринки",
                R.drawable._im_702502b5_69ff_4c16_9148_80fedb196c89
            ),
            ServicesCard(
                "VIP-комната",
                R.drawable.wfcubjudis72rza2fjhpywblwwu7rtso
            ),
            ServicesCard(
                "Бесплатный Wi-Fi",
                R.drawable._im_702502b5_69ff_4c16_9148_80fedb196c89
            ),
            ServicesCard(
                "Прямые трансляции",
                R.drawable.wfcubjudis72rza2fjhpywblwwu7rtso
            ),
            ServicesCard(
                "Настольные игры",
                R.drawable._im_702502b5_69ff_4c16_9148_80fedb196c89
            ),
            ServicesCard(
                "Бесплатная парковка",
                R.drawable.wfcubjudis72rza2fjhpywblwwu7rtso
            )

        )
    }
}