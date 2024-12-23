package com.example.hillsloungeapplication.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hillsloungeapplication.R
import com.example.hillsloungeapplication.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        val toastText = "This is a Toast text!"


        binding.profileFragmentSupportImButton.setOnClickListener {
            Toast.makeText(activity, toastText, Toast.LENGTH_SHORT).show()
        }

        binding.profileFragmentPushSettings.setOnClickListener {
            Toast.makeText(activity, toastText, Toast.LENGTH_SHORT).show()
        }

        binding.profileFragmentLoyaltyInfo.setOnClickListener {
            Toast.makeText(activity, toastText, Toast.LENGTH_SHORT).show()
        }

        binding.profileFragmentPushSettings.setOnClickListener {
            Toast.makeText(activity, toastText, Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}