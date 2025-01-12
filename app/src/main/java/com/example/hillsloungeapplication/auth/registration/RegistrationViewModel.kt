package com.example.hillsloungeapplication.auth.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hillsloungeapplication.auth.registration.database.UserDao
import com.example.hillsloungeapplication.auth.registration.database.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel(private val userDao: UserDao) : ViewModel() {

    fun registerUser(username: String, number: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val exists = userDao.checkUserExists(username) // Проверка пользователя
                if (exists != null) {
                    withContext(Dispatchers.Main) {
                        onResult(false, "Такой пользователь уже существует")
                    }
                } else {
                    val user = Users(userName = username, userNumber = number, userPassword = password)
                    userDao.insert(user)
                    withContext(Dispatchers.Main) {
                        onResult(true, "Пользователь зарегистрирован")
                    }
                }
            } catch (e: Exception) {
                Log.e("RegistrationViewModel", "Ошибка регистрации", e)
                withContext(Dispatchers.Main) {
                    onResult(false, "Ошибка регистрации")
                }
            }
        }
    }
}


