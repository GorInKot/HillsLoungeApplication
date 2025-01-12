package com.example.hillsloungeapplication.Profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hillsloungeapplication.auth.UserRepository
import com.example.hillsloungeapplication.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

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
        loadUserName()

        return view

    }

    private fun setupListeners() {
        binding.profileFragmentSupportImButton.setOnClickListener {
            Toast.makeText(activity, viewModel.toastText, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "this is log from profileFragment (support pressed)")
        }

        binding.profileFragmentPushSettings.setOnClickListener {
            Toast.makeText(activity, viewModel.toastText, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "this is log from profileFragment (pushSettings pressed)")
        }

        binding.profileFragmentLoyaltyInfo.setOnClickListener {
            Toast.makeText(activity, viewModel.toastText, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "this is log from profileFragment (loyaltyInfo pressed)")
        }

        binding.profileFragmentAboutApp.setOnClickListener {
            Toast.makeText(activity, viewModel.toastText, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "this is log from profileFragment (aboutApp pressed)")
        }
    }

    private fun loadUserName() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            userRepository.getUserName(userId) { userName ->
                if (userName != null) {
                    // Устанавливаем имя пользователя в TextView
                    binding.profileFragmentUserName.text = userName
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}