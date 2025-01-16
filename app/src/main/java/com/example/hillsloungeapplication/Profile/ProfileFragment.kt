package com.example.hillsloungeapplication.Profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.hillsloungeapplication.MainActivity
import com.example.hillsloungeapplication.R
import com.example.hillsloungeapplication.auth.UserRepository
import com.example.hillsloungeapplication.auth.signIn.SignInFragment
import com.example.hillsloungeapplication.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ProfileViewModel

    private val userRepository = UserRepository()
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        // Инициализация FirebaseAuth
        auth = FirebaseAuth.getInstance()

        setupListeners()
        loadUserData()

        binding.fragmentHomeImageButton.setOnClickListener {
            onLogout()
        }

        return view

    }

    private fun setupListeners() {
        binding.profileFragmentSupportImButton.setOnClickListener {
            Toast.makeText(activity, viewModel.toastTextSupport, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "this is log from profileFragment (support pressed)")
        }

        binding.profileFragmentPushSettings.setOnClickListener {
            Toast.makeText(activity, viewModel.toastTextPushSettings, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "this is log from profileFragment (pushSettings pressed)")
        }

        binding.profileFragmentLoyaltyInfo.setOnClickListener {
            Toast.makeText(activity, viewModel.toastTextLoyalty, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "this is log from profileFragment (loyaltyInfo pressed)")
        }

        binding.profileFragmentAboutApp.setOnClickListener {
            Toast.makeText(activity, viewModel.toastTextAboutApp, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "this is log from profileFragment (aboutApp pressed)")
        }
    }

    private fun loadUserData() {
        viewLifecycleOwner.lifecycleScope.launch {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val userId = currentUser.uid
                userRepository.getUserData(userId) { userData ->
                    if (userData != null) {
                        // Устанавливаем имя пользователя в TextView
                        binding.profileFragmentUserName.text = userData.name
                        binding.profileFragmentUserTelephone.text = userData.phone
                    } else {
                        // Показываем сообщение, если имя пользователя не найдено
                        binding.profileFragmentUserName.text = "Имя пользователя не найдено"
                    }
                }
            } else {
                // Если пользователь не авторизован
                binding.profileFragmentUserName.text = "Пользователь не авторизован"
            }
        }
    }

    // Метод для выхода из учетной записи
    private fun onLogout() {
        // Очистка данных об авторизации (например, флаг или токен)
        // Тут можно сбросить состояние авторизации (например, SharedPreferences или другую логику)
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        // Очистить все данные
        editor.clear()
        editor.apply()

        // hide BottomNavigationView
        (activity as? MainActivity)?.hideBottomNavigation()

        // open SignInFragment
        (activity as? MainActivity)?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame_layout, SignInFragment())
            ?.commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}