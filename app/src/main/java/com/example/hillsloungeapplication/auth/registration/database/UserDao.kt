package com.example.hillsloungeapplication.auth.registration.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: Users)

    @Query("SELECT * FROM users WHERE user_name = :username")
    suspend fun checkUserExists(username: String): Users?
}