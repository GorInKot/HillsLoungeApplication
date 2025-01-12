package com.example.hillsloungeapplication.auth

import android.util.Log
import com.google.firebase.database.FirebaseDatabase

class UserRepository {

    // Получение данных пользователя по UID из Firebase Realtime Database
    fun getUserName(userId: String, callback: (String?) -> Unit) {
        val database = FirebaseDatabase.getInstance().reference
        database.child("users").child(userId).child("name").get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val userName = snapshot.getValue(String::class.java)
                    callback(userName)
                } else {
                    callback(null) // Имя пользователя не найдено
                }
            }
            .addOnFailureListener {
                callback(null) // Ошибка при запросе
            }
    }
}
