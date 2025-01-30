package com.example.hillsloungeapplication.Places.Details.Reservating

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.hillsloungeapplication.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReservationBottomSheet(private val telegramLink: String) : BottomSheetDialogFragment() {

    private lateinit var datePicker: NumberPicker
    private lateinit var timePicker: NumberPicker
    private lateinit var guestCountTextView: TextView

    private val viewModel: ReservationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.reservation_bottom_sheet, container, false)

        view.viewTreeObserver.addOnGlobalLayoutListener {
            val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        datePicker = view.findViewById(R.id.datePicker)
        timePicker = view.findViewById(R.id.timePicker)
        guestCountTextView = view.findViewById(R.id.guestCount)

        val btnMinus = view.findViewById<Button>(R.id.btnMinus)
        val btnPlus = view.findViewById<Button>(R.id.btnPlus)
        val btnReserve = view.findViewById<Button>(R.id.btnReserve)

        setupObservers()

        btnMinus.setOnClickListener { viewModel.decreaseGuestCount() }
        btnPlus.setOnClickListener { viewModel.increaseGuestCount() }
        btnReserve.setOnClickListener {
            Log.d("TelegramLink", "Ссылка: $telegramLink")

            sendReservationToTelegram()
        }

        return view
    }

    private fun setupObservers() {
        viewModel.guestCount.observe(viewLifecycleOwner) { count ->
            guestCountTextView.text = "$count ${viewModel.getGuestLabel(count)}"
        }

        viewModel.dates.observe(viewLifecycleOwner) { dates ->
            datePicker.minValue = 0
            datePicker.maxValue = dates.size - 1
            datePicker.displayedValues = dates.toTypedArray()
        }

        viewModel.timeSlots.observe(viewLifecycleOwner) { timeSlots ->
            timePicker.minValue = 0
            timePicker.maxValue = timeSlots.size - 1
            timePicker.displayedValues = timeSlots.toTypedArray()
        }
    }

    private fun sendReservationToTelegram() {
        val selectedDate = datePicker.displayedValues[datePicker.value]
        val selectedTime = timePicker.displayedValues[timePicker.value]

        val message = viewModel.generateTelegramMessage(selectedDate, selectedTime)
        val encodedMessage = Uri.encode(message)
        val fullLink = "$telegramLink?text=$encodedMessage"

        Log.d("TelegramDebug", "Готовая ссылка: $fullLink") // Логируем ссылку

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fullLink))
//        intent.setPackage("org.telegram.messenger")

        // Проверяем, может ли Android открыть ссылку
        val packageManager = requireContext().packageManager
        val resolvedActivity = intent.resolveActivity(packageManager)

        Log.d("TelegramDebug", "resolveActivity: ${resolvedActivity?.packageName ?: "null"}")

        if (resolvedActivity != null) {
            startActivity(intent)
        } else {
            Log.e("TelegramDebug", "Telegram не найден!") // Ошибка в лог
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(fullLink))
            startActivity(browserIntent)
            Toast.makeText(requireContext(), "Telegram не установлен", Toast.LENGTH_SHORT).show()
        }

        dismiss()
    }


}

