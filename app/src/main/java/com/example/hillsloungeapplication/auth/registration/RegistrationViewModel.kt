package com.example.hillsloungeapplication.auth.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    sealed class RegistrationState {
        object Idle: RegistrationState()
        object Loading: RegistrationState()
        data class Success(val message: String): RegistrationState()
        data class Error(val error: String): RegistrationState()
    }

    private val _registrationState = MutableLiveData<RegistrationState>()
    val registrationState: LiveData<RegistrationState> get() = _registrationState

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun registerUser(name: String, email: String, number: String, password: String, confirmPassword: String) {
        if (name.isBlank() || email.isBlank() || number.isBlank() || password.isBlank()) {
            _registrationState.postValue(RegistrationState.Error("Заполните все поля"))
            return
        }

        if (!isEmailValid(email)) {
            _registrationState.postValue(RegistrationState.Error("Введите корректный email"))
            return
        }

        if (password != confirmPassword) {
            _registrationState.postValue(RegistrationState.Error("Пароли не совпадают"))
            return
        }

        _registrationState.postValue(RegistrationState.Loading)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        saveUserToDatabase(userId, name, email, number, password)
                    } else {
                        _registrationState.postValue(RegistrationState.Error("Ошибка регистрации: UserId не найден"))
                    }
                } else {
                    val errorMessage = task.exception?.message ?: "Ошибка регистрации"
                    _registrationState.postValue(RegistrationState.Error(errorMessage))
                }
            }
    }

    private fun saveUserToDatabase(userId: String, username: String, email: String, number: String, password: String) {
        val user = mapOf(
            "name" to username,
            "email" to email,
            "number" to number,
            "password" to password
        )

        database.child("users").child(userId).setValue(user)
            .addOnSuccessListener {
                _registrationState.postValue(RegistrationState.Success("Пользователь успешно сохранен"))
            }
            .addOnFailureListener { e ->
                _registrationState.postValue(RegistrationState.Error("Ошибка сохранения пользователя"))
            }
    }






}