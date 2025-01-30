package com.example.hillsloungeapplication.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.hillsloungeapplication.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.hillsloungeapplication.databinding.FragmentCardDetailsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class CardDetailsBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCardDetailsBottomSheetBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_HEADER = "header"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_IMAGE_URL = "image_url"

        // Функция для создания нового экземпляра фрагмента
        fun newInstance(header: String, description: String, imageResId: Int): CardDetailsBottomSheetFragment {
            val fragment = CardDetailsBottomSheetFragment()
            val args = Bundle()
            args.putString(ARG_HEADER, header)
            args.putString(ARG_DESCRIPTION, description)
            args.putInt(ARG_IMAGE_URL, imageResId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Используем ViewBinding для инфлейта разметки
        _binding = FragmentCardDetailsBottomSheetBinding.inflate(inflater, container, false)

        // Получаем данные из аргументов
        val header = arguments?.getString(ARG_HEADER) ?: ""
        val description = arguments?.getString(ARG_DESCRIPTION) ?: ""
        val imageResId = arguments?.getInt(ARG_IMAGE_URL) ?: R.drawable.placeholder

        // Заполняем представления данными через ViewBinding
        binding.cardDetailsTitle.text = header
        binding.cardDetailsDescription.text = description
        binding.cardDetailsImage.setImageResource(imageResId)

        binding.closeButton.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Устанавливаем размер BottomSheet
        val bottomSheetDialog = dialog as BottomSheetDialog?
        bottomSheetDialog?.behavior?.setState(BottomSheetBehavior.STATE_EXPANDED)
        // увеличение высоты открывающегося окна
        bottomSheetDialog?.behavior?.peekHeight = (resources.displayMetrics.heightPixels * 1).toInt()

//        bottomSheetDialog?.behavior?.peekHeight = (resources.displayMetrics.heightPixels * 0.8).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

