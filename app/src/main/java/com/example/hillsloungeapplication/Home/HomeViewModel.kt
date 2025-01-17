package com.example.hillsloungeapplication.Home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hillsloungeapplication.Home.recyclerView.BigRV.Card
import com.example.hillsloungeapplication.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val _cardsBRV = MutableLiveData<List<Card>>()
    val cardsBRV: LiveData<List<Card>> = _cardsBRV

    private val _cardsSRV = MutableLiveData<List<Int>>()
    val cardsSRV: LiveData<List<Int>> = _cardsSRV

    private val _currentIndexBRV = MutableLiveData(0)
    val currentIndexBRV: LiveData<Int> = _currentIndexBRV

    private val _currentIndexSRV = MutableLiveData(0)
    val currentIndexSRV: LiveData<Int> = _currentIndexSRV

    init {
        _cardsBRV.value = listOf(
            Card("Новогодняя вечеринка в Hills Lounge | Саларьево",
                "Мы работаем для Вас с 20:00 и до утра!",
                "https://s0.rbk.ru/v6_top_pics/media/img/4/04/346843326750044.jpg",
            ),

            Card("Новогодняя вечеринка в Hills Lounge | Румянцево",
                "Мы работаем для Вас с 20:00 и до утра!",
                "https://png.pngtree.com/thumb_back/fw800/background/20230612/pngtree-images-of-winter-and-white-background-wallpapers-free-download-image_2935697.jpg",
            ),

            Card("Новогодняя вечеринка в Hills Lounge | Солнцево",
                "Мы работаем для Вас с 20:00 и до утра!",
                "https://img1.akspic.ru/previews/5/3/0/9/7/179035/179035-voda-gora-gidroresursy-rastenie-oblako-550x310.jpg",
            )
        )

        _cardsSRV.value = listOf(
            R.drawable._im_702502b5_69ff_4c16_9148_80fedb196c89,
            R.drawable.wfcubjudis72rza2fjhpywblwwu7rtso,
            R.drawable._im_702502b5_69ff_4c16_9148_80fedb196c89,
            R.drawable.wfcubjudis72rza2fjhpywblwwu7rtso,
            R.drawable._im_702502b5_69ff_4c16_9148_80fedb196c89,
            R.drawable.wfcubjudis72rza2fjhpywblwwu7rtso,
            R.drawable._im_702502b5_69ff_4c16_9148_80fedb196c89,
            R.drawable.wfcubjudis72rza2fjhpywblwwu7rtso,
            R.drawable._im_702502b5_69ff_4c16_9148_80fedb196c89,
            R.drawable.wfcubjudis72rza2fjhpywblwwu7rtso,
            R.drawable._im_702502b5_69ff_4c16_9148_80fedb196c89,
            R.drawable.wfcubjudis72rza2fjhpywblwwu7rtso
        )
    }

    fun startAutoScrollBRV() {
        viewModelScope.launch {
            while (true) {
                delay(5000)
                _currentIndexBRV.value = (_currentIndexBRV.value!! + 1) % (_cardsBRV.value?.size ?: 1)
            }
        }
    }

    fun startAutoScrollSRV() {
        viewModelScope.launch {
            while (true) {
                delay(4500)
                _currentIndexSRV.value = (_currentIndexSRV.value!! + 1) % (_cardsSRV.value?.size ?: 1)
            }
        }
    }

    fun logout() {
        val sharedPreferences = getApplication<Application>().getSharedPreferences(
            "user_prefs",
            Context.MODE_PRIVATE
        )
        sharedPreferences.edit().clear().apply()
    }
}