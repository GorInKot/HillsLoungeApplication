package com.example.hillsloungeapplication.Home

class NewsFieldsManager {
    private val mNewsFields = arrayOf(
        "Новый год в Hills Lounge Саларьево",
        "Работаем для Вас 31 декабря с 20:00 и до утра!"
    )
    var currentIndex = 0

    fun loadNextField() {
        currentIndex++
        if (currentIndex >= mNewsFields.size) {
            currentIndex = 0
        }
    }

    fun loadPreviousField() {
        currentIndex--
        if (currentIndex < 0) {
            currentIndex = mNewsFields.size - 1
        }
    }
}
