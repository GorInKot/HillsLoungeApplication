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
import com.example.hillsloungeapplication.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ProfileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.profileFragmentSupportImButton.setOnClickListener {
            Toast.makeText(activity, viewModel.toastText, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "this is log from profileFragment (support pressed)" )
        }

        binding.profileFragmentPushSettings.setOnClickListener {
            Toast.makeText(activity, viewModel.toastText, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "this is log from profileFragment (support pressed)" )
        }

        binding.profileFragmentLoyaltyInfo.setOnClickListener {
            Toast.makeText(activity, viewModel.toastText, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "this is log from profileFragment (loyaltyInfo pressed)" )
        }

        binding.profileFragmentAboutApp.setOnClickListener {
            Toast.makeText(activity, viewModel.toastText, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "this is log from profileFragment (pushSettings pressed)" )
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}