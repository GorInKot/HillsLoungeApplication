package com.example.hillsloungeapplication.auth.resetPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import com.example.hillsloungeapplication.R
import com.example.hillsloungeapplication.databinding.FragmentResetPasswordBinding


class ResetPasswordFragment : Fragment() {

    private lateinit var viewModel: ResetPasswordViewModel
    private lateinit var editTextInput: EditText
    private lateinit var buttonSendCode: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var verificationLayout: LinearLayout
    private lateinit var editTextCode: EditText
    private lateinit var editTextNewPassword: EditText
    private lateinit var buttonVerify: Button

    private var userEmail: String? = null
    private var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_reset_password, container, false)
        viewModel = ViewModelProvider(this).get(ResetPasswordViewModel::class.java)

        editTextInput = view.findViewById(R.id.editTextInput)
        buttonSendCode = view.findViewById(R.id.buttonSendCode)
        progressBar = view.findViewById(R.id.progressBar)
        verificationLayout = view.findViewById(R.id.verificationLayout)
        editTextCode = view.findViewById(R.id.editTextCode)
        editTextNewPassword = view.findViewById(R.id.editTextNewPassword)
        buttonVerify = view.findViewById(R.id.buttonVerify)

        buttonSendCode.setOnClickListener {
            val input = editTextInput.text.toString().trim()
            if (input.isEmpty()) {
                Toast.makeText(requireContext(), "Введите email или номер телефона", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            findUser(input)
        }

        buttonVerify.setOnClickListener {
            val code = editTextCode.text.toString().trim()
            val newPassword = editTextNewPassword.text.toString().trim()
            if (code.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Введите код и новый пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            verifyCode(code, newPassword)
        }

        return view
    }

    private fun findUser(input: String) {
        progressBar.visibility = View.VISIBLE
        buttonSendCode.isEnabled = false

        viewModel.findUser(input) { found, uid, email ->
            progressBar.visibility = View.GONE
            buttonSendCode.isEnabled = true

            if (found && email != null) {
                userEmail = email
                userId = uid
                sendVerificationCode(email)
            } else {
                Toast.makeText(requireContext(), "Пользователь не найден", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendVerificationCode(email: String) {
        progressBar.visibility = View.VISIBLE
        viewModel.sendVerificationCode(email) { success, message ->
            progressBar.visibility = View.GONE
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

            if (success) {
                verificationLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun verifyCode(code: String, newPassword: String) {
        userEmail?.let { email ->
            viewModel.verifyCode(email, code) { success, message ->
                if (success) {
                    userId?.let { uid ->
                        updatePassword(uid, newPassword)
                    }
                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updatePassword(uid: String, newPassword: String) {
        progressBar.visibility = View.VISIBLE
        viewModel.updatePassword(uid, newPassword) { success, message ->
            progressBar.visibility = View.GONE
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

            if (success) {
                findNavController().popBackStack()
            }
        }
    }
}