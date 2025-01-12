package com.example.hillsloungeapplication.auth.registration.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class Users(

    @PrimaryKey(autoGenerate = false)
    var userId: Int = 0,

    @ColumnInfo(name = "user_name")
    var userName : String,

    @ColumnInfo(name = "user_number")
    var userNumber: String,

    @ColumnInfo(name = "user_password")
    var userPassword: String
)
