package com.example.hillsloungeapplication.auth.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hillsloungeapplication.MainActivity
import com.example.hillsloungeapplication.R
import com.example.hillsloungeapplication.auth.registration.RegistrationFragment
import com.example.hillsloungeapplication.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SignInViewModel



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

        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

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

            viewModel.signInUser(email, password)
        }

        viewModel.signInState.observe(viewLifecycleOwner, {state ->
            when (state) {
                is SignInViewModel.SignInState.Idle -> {
                    // ничего не делаем
                }
                is SignInViewModel.SignInState.Loading -> {
                    // TODO - показать загрузку (а надо ли?)
                }
                is SignInViewModel.SignInState.Success -> {
                    (activity as? MainActivity)?.onAuthenticationSuccess()
                }
                is SignInViewModel.SignInState.Error -> {
                    showAuthenticationErrorDialog(state.message)
                }
            }
        })
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


