package com.example.hillsloungeapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.hillsloungeapplication.Home.HomeFragment
import com.example.hillsloungeapplication.Profile.ProfileFragment
import com.example.hillsloungeapplication.Places.PlacesFragment
import com.example.hillsloungeapplication.auth.signIn.SignInFragment
import com.example.hillsloungeapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        // Открываем SignInFragment при запуске
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, SignInFragment())
                .commit()
            hideBottomNavigation()
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.places -> replaceFragment(PlacesFragment())
                else -> { }
            }
            true
        }
    }

    fun hideBottomNavigation() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    fun showBottomNavigation() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()

        // Показываем BottomNavigationView для фрагментов, где он нужен
        if (fragment is HomeFragment || fragment is ProfileFragment || fragment is PlacesFragment) {
            showBottomNavigation()
        } else {
            hideBottomNavigation()
        }
    }

    // Метод вызывается при успешной авторизации
    fun onAuthenticationSuccess() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, HomeFragment())
            .commit()
        showBottomNavigation() // Показываем BottomNavigationView после авторизации
    }

}

