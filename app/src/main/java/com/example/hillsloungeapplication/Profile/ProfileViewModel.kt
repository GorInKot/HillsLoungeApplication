package com.example.hillsloungeapplication.Profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hillsloungeapplication.auth.UserRepository
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel : ViewModel() {

    // Тексты для тостов
    val toastTextSupport = "This is a Toast text!"
    val toastTextPushSettings = "This is a Toast text!"
    val toastTextLoyalty = "This is a Toast text!"
    val toastTextAboutApp = "This is a Toast text!"

    // LiveData для хранения данных пользователя
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _userPhone = MutableLiveData<String>()
    val userPhone: LiveData<String> get() = _userPhone

    private val _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIn: LiveData<Boolean> get() = _isUserLoggedIn

    private val userRepository = UserRepository()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Загрузка данных пользователя
    fun loadUserData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            userRepository.getUserData(userId) { userData ->
                if (userData != null) {
                    _userName.value = userData.name
                    _userPhone.value = userData.phone
                    _isUserLoggedIn.value = true
                } else {
                    _userName.value = "Имя пользователя не найдено"
                    _isUserLoggedIn.value = false
                }
            }
        } else {
            _userName.value = "Пользователь не авторизован"
            _isUserLoggedIn.value = false
        }
    }

    // Логика для выхода из учетной записи
    fun onLogout(context: Context) {
        // Очистка данных об авторизации (например, флаг или токен)
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Notify that user has logged out
        _isUserLoggedIn.value = false
    }
}
