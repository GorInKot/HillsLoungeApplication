package com.example.hillsloungeapplication.auth.registration

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hillsloungeapplication.R
import com.example.hillsloungeapplication.auth.signIn.SignInFragment
import com.example.hillsloungeapplication.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewLogin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, SignInFragment())
                .addToBackStack(null)
                .commit()
        }

        // Обработка создания учетной записи (пока заглушка)
        binding.buttonRegistration.setOnClickListener {
            Toast.makeText(requireContext(), "Account created successfully!", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack() // Возврат к SignInFragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

