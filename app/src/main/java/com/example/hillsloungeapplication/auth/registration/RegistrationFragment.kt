package com.example.hillsloungeapplication.auth.registration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hillsloungeapplication.R
import com.example.hillsloungeapplication.auth.signIn.SignInFragment
import com.example.hillsloungeapplication.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.registrationState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RegistrationViewModel.RegistrationState.Idle -> {
                /* ничего не делать */
                }

                is RegistrationViewModel.RegistrationState.Loading -> {
                    /* TODO - добавить progressBar для отображения загрузки
                    binding.progressBar.visibility = View.VISIBLE
                     */
                }

                is RegistrationViewModel.RegistrationState.Success -> {
                    //binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                }

                is RegistrationViewModel.RegistrationState.Error -> {
                    //binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Обработка создания учетной записи
        binding.buttonRegistration.setOnClickListener {
            Log.d("Buttons", "Button Registration pressed")
            val name = binding.editTextName.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val number = binding.editTextNumber.text.toString()
            val password = binding.editTextPassword.text.toString()
            val passwordConfirm = binding.editTextConfirmPassword.text.toString()

            viewModel.registerUser(name, email, number, password, passwordConfirm)
        }

        // Переход к SignInFragment
        binding.textViewLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, SignInFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



