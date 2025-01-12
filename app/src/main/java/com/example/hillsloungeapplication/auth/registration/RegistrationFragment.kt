package com.example.hillsloungeapplication.auth.registration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hillsloungeapplication.R
import com.example.hillsloungeapplication.auth.signIn.SignInFragment
import com.example.hillsloungeapplication.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Переход к SignInFragment
        binding.textViewLogin.setOnClickListener {
            navigateToLogin()
        }

        // Обработка создания учетной записи
        binding.buttonRegistration.setOnClickListener {
            Log.d("Buttons",
                "Button Registration pressed")
            handleRegistration()
        }

        // Пример использования кнопки testButton (без получения пользователей)
        binding.testButton.setOnClickListener {
            Toast.makeText(requireContext(),
                "Test Button Clicked",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToLogin() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, SignInFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    private fun handleRegistration() {
        val name = binding.editTextName.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val number = binding.editTextNumber.text.toString()
        val password = binding.editTextPassword.text.toString()
        val passwordConfirm = binding.editTextConfirmPassword.text.toString()

        if (name.isBlank() || email.isBlank() || number.isBlank() || password.isBlank()) {
            Toast.makeText(requireContext(),
                "Заполните все поля",
                Toast.LENGTH_LONG).show()
            return
        }

        if (!isEmailValid(email)) {
            Toast.makeText(requireContext(),
                "Введите корректный email",
                Toast.LENGTH_SHORT).show()
            return
        }

        if (password != passwordConfirm) {
            Toast.makeText(requireContext(),
                "Пароли не совпадают",
                Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        Log.d("Registration",
                            "User ID: $userId")
                        saveUserToDatabase(userId, name, email, number, password)
                        navigateToLogin()
                    } else {
                        Log.e("Registration",
                            "User ID is null")
                    }
                } else {
                    val errorMessage = task.exception?.message ?: "Ошибка регистрации"
                    Log.e("Registration",
                        "Error: $errorMessage")
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun saveUserToDatabase(userId: String, username: String, email: String, number: String, password: String) {
        val database = FirebaseDatabase.getInstance().reference

        val user = mapOf(
            "name" to username,
            "email" to email,
            "number" to number,
            "password" to password

        )

        database.child("users").child(userId).setValue(user)
            .addOnSuccessListener {
                Toast.makeText(requireContext(),
                    "Пользователь успешно сохранен",
                    Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(),
                    "Ошибка сохранения пользователя: ${e.localizedMessage}",
                    Toast.LENGTH_SHORT).show()
            }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



