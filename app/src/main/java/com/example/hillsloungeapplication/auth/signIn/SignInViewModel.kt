package com.example.hillsloungeapplication.auth.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hillsloungeapplication.auth.UserRepository
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userRepository = UserRepository()

    // LiveData для отслеживания состояний
    private val _signInState = MutableLiveData<SignInState>()
    val signInState: LiveData<SignInState> get() = _signInState

    // ? - что за класс такой
    sealed class SignInState {
        object Idle: SignInState()
        object Loading: SignInState()
        data class Success(val userName: String): SignInState()
        data class Error(val message: String): SignInState()

    }

    fun signInUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _signInState.value = SignInState.Error("Заполните все поля")
            return
        }
        _signInState.value = SignInState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        userRepository.getUserData(userId) { userName ->
                            if (userName != null) {
                                _signInState.postValue(SignInState.Success(userName.name))
                            } else {
                                _signInState.postValue(SignInState.Error("Имя пользователя не найдено"))
                            }
                        }
                    } else {
                        _signInState.postValue(SignInState.Error("Ошибка получения данных пользователя"))
                    }
                } else {
                    val errorMessage = task.exception?.localizedMessage ?: "Ошибка авторизации"
                    _signInState.postValue(SignInState.Error(errorMessage))
                }
            }
    }
}
