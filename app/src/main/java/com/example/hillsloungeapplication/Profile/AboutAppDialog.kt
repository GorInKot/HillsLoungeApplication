package com.example.hillsloungeapplication.Profile

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.hillsloungeapplication.R

class AboutAppDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.attributes?.dimAmount = 0.5f

        return inflater.inflate(R.layout.dialog_about_app, container, false)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_about_app, null)

        // Закрытие диалога по кнопке
        val okButton = view.findViewById<Button>(R.id.ok_button)
        okButton.setOnClickListener { dismiss() }

        builder.setView(view)
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        applyBlurEffect()
    }

    private fun applyBlurEffect() {
        val blurEffect = BlurMaskFilter(15f, BlurMaskFilter.Blur.NORMAL)
        dialog?.window?.decorView?.background?.colorFilter =
            PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
    }
}