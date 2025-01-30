package com.example.hillsloungeapplication.auth

import com.example.hillsloungeapplication.auth.registration.database.UserProfile
import com.google.firebase.database.FirebaseDatabase

class UserRepository {

    // Получение данных пользователя по UID из Firebase Realtime Database
    fun getUserData(userId: String, callback: (UserProfile?) -> Unit) {
        val database = FirebaseDatabase.getInstance().reference
        database.child("users").child(userId).get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    val name = dataSnapshot.child("name").getValue(String::class.java)
                    val phone = dataSnapshot.child("number").getValue(String::class.java)
                    if (name != null && phone != null) {
                        callback(UserProfile(name, phone))
                    } else {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }

}
