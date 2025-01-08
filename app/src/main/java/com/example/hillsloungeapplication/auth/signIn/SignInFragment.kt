package com.example.hillsloungeapplication.auth.signIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.hillsloungeapplication.MainActivity
import com.example.hillsloungeapplication.R
import com.example.hillsloungeapplication.auth.registration.RegistrationFragment
import com.example.hillsloungeapplication.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    // Заглушка для проверки учетных данных
    private val validUsername = "user"
    private val validPassword = "password"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Инициализация binding
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Переход к RegistrationFragment
        binding.textViewCreateAccount.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, RegistrationFragment())
                .addToBackStack(null)
                .commit()
        }

        // Обработка нажатия кнопки авторизации
        binding.buttonSignIn.setOnClickListener {
            val username = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (username == validUsername && password == validPassword) {
                // Успешная авторизация
                (activity as? MainActivity)?.onAuthenticationSuccess()
            } else {
                // Ошибка авторизации
//                Toast.makeText(requireContext(), "Неверный логин или пароль", Toast.LENGTH_LONG).show()
                showAuthenticationErrorDialog()
            }
        }
    }

    private fun showAuthenticationErrorDialog() {
        // Создаем и показываем диалог с ошибкой
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Ошибка авторизации")
            .setMessage("Неверный логин или пароль")
            .setPositiveButton("ОК") { dialog, _ ->
                dialog.dismiss() // Закрыть диалог при нажатии на кнопку "ОК"
            }

        // Создаем и показываем AlertDialog
        val dialog = builder.create()
        dialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Очищаем binding, чтобы избежать утечек памяти
        _binding = null
    }
}
