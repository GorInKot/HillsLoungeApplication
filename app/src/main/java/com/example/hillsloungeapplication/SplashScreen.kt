package com.example.hillsloungeapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Указываем тему SplashScreen
        //setTheme(R.style.Theme_HillsLoungeApplication_SplashScreen)

        // Задержка перед переходом на MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Закрываем SplashActivity
        }, 3000) // Задержка 2 секунды
    }
}