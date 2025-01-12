package com.example.hillsloungeapplication.auth.signIn

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.hillsloungeapplication.MainActivity
import com.example.hillsloungeapplication.R
import com.example.hillsloungeapplication.auth.UserRepository
import com.example.hillsloungeapplication.auth.registration.RegistrationFragment
import com.example.hillsloungeapplication.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private val userRepository = UserRepository()

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

        // Инициализация FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Переход к RegistrationFragment
        binding.textViewCreateAccount.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, RegistrationFragment())
                .addToBackStack(null)
                .commit()
        }

        // Обработка нажатия кнопки авторизации
        binding.buttonSignIn.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(),
                    "Заполните все поля",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            signInUser(email, password)
        }
    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        Log.d("SignInFragment", "Fetching user data for userId: $userId")
                        userRepository.getUserName(userId) { userName ->
                            activity?.runOnUiThread {
                                if (userName != null) {
                                    Log.d("SignInFragment", "User name fetched: $userName")
                                    Toast.makeText(requireContext(),
                                        "Добро пожаловать, $userName",
                                        Toast.LENGTH_SHORT).show()
                                } else {
                                    Log.e("SignInFragment", "User name is null.")
                                    Toast.makeText(requireContext(),
                                        "Имя пользователя не найдено",
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } else {
                        Log.e("SignInFragment", "UserId is null after sign-in.")
                        Toast.makeText(requireContext(),
                            "Ошибка получения данных пользователя",
                            Toast.LENGTH_SHORT).show()
                    }
                    (activity as? MainActivity)?.onAuthenticationSuccess()
                } else {
                    val errorMessage = task.exception?.localizedMessage ?: "Ошибка авторизации"
                    Log.e("SignInFragment", "Sign-in failed: ${task.exception}")
                    showAuthenticationErrorDialog(errorMessage)
                }
            }
    }


    private fun showAuthenticationErrorDialog(message: String) {
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
        _binding = null
    }
}


