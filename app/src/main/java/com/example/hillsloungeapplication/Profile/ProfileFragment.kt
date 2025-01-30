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

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()

        // Наблюдаем за LiveData для обновления UI
        viewModel.userName.observe(viewLifecycleOwner) { userName ->
            binding.profileFragmentUserName.text = userName
        }

        viewModel.userPhone.observe(viewLifecycleOwner) { userPhone ->
            binding.profileFragmentUserTelephone.text = userPhone
        }

        viewModel.isUserLoggedIn.observe(viewLifecycleOwner) { isLoggedIn ->
            if (!isLoggedIn) {
                // Если пользователь не авторизован, показываем экран входа
                onLogout()
            }
        }

        setupListeners()
        viewModel.loadUserData() // Загрузка данных пользователя

        return view
    }

    private fun setupListeners() {
        binding.supportSection.setOnClickListener {
            Toast.makeText(activity, viewModel.toastTextSupport, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "this is log from profileFragment (support pressed)")
        }

        binding.settingsPushesSection.setOnClickListener {
            Toast.makeText(activity, viewModel.toastTextPushSettings, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "this is log from profileFragment (pushSettings pressed)")
        }

        binding.loyaltySection.setOnClickListener {
            Toast.makeText(activity, viewModel.toastTextLoyalty, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "this is log from profileFragment (loyaltyInfo pressed)")
        }

        binding.aboutAppSection.setOnClickListener {
            Log.d("TAG", "this is log from profileFragment (aboutApp pressed)")
            val dialog = AboutAppDialog()
            dialog.show(parentFragmentManager, "AboutAppDialog")
        }

        binding.fragmentHomeImageButton.setOnClickListener {
            viewModel.onLogout(requireContext())
        }
    }

    private fun onLogout() {
        // Прячем BottomNavigationView, если нужно
        (activity as? MainActivity)?.hideBottomNavigation()

        // Переход к экрану авторизации
        (activity as? MainActivity)?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame_layout, SignInFragment())
            ?.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}