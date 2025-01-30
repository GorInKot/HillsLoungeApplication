package com.example.hillsloungeapplication.Places.Details.Reservating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ReservationViewModel : ViewModel() {

    private val _guestCount = MutableLiveData(2)
    val guestCount: LiveData<Int> = _guestCount

    private val _dates = MutableLiveData<List<String>>()
    val dates: LiveData<List<String>> = _dates

    private val _timeSlots = MutableLiveData<List<String>>()
    val timeSlots: LiveData<List<String>> = _timeSlots

    init {
        generateDates()
        generateTimeSlots()
    }

    fun increaseGuestCount() {
        if (_guestCount.value!! < 20) {
            _guestCount.value = _guestCount.value!! + 1
        }
    }

    fun decreaseGuestCount() {
        if (_guestCount.value!! > 1) {
            _guestCount.value = _guestCount.value!! - 1
        }
    }

    private fun generateDates() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMMM", Locale("ru"))

        val datesList = mutableListOf<String>()
        for (i in 0..5) {
            datesList.add(dateFormat.format(calendar.time))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        _dates.value = datesList
    }

    private fun generateTimeSlots() {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val closingHour = if (dayOfWeek in listOf(Calendar.FRIDAY, Calendar.SATURDAY)) 2 else 1

        val slots = mutableListOf<String>()
        for (hour in 12..23) {
            for (minute in listOf("00", "15", "30", "45")) {
                slots.add("$hour:$minute")
            }
        }

        for (hour in 0..closingHour) {
            for (minute in listOf("00", "15", "30", "45")) {
                slots.add("${"%02d".format(hour)}:$minute")
            }
        }
        _timeSlots.value = slots
    }

    fun getGuestLabel(count: Int): String {
        return when {
            count == 1 -> "гость"
            count in 2..4 -> "гостя"
            else -> "гостей"
        }
    }

    fun generateTelegramMessage(selectedDate: String, selectedTime: String): String {
        return """
            🔔 *Новая бронь стола* 🔔
            📅 Дата: *$selectedDate*
            ⏰ Время: *$selectedTime*
            👥 Количество гостей: *${_guestCount.value}*
        """.trimIndent()
    }
}
