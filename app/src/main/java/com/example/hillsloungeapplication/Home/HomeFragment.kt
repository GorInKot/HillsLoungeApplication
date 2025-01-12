package com.example.hillsloungeapplication.Home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hillsloungeapplication.Home.recyclerView.Card
import com.example.hillsloungeapplication.Home.recyclerView.CustomRecyclerAdapter
import com.example.hillsloungeapplication.MainActivity
import com.example.hillsloungeapplication.R
import com.example.hillsloungeapplication.auth.signIn.SignInFragment
import com.example.hillsloungeapplication.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CustomRecyclerAdapter
    private var currentIndex = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerView = binding.homeFragmentRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.fragmentHomeImageButton.setOnClickListener {
            onLogout()
        }

        val cards = listOf(
            Card("Новогодняя вечеринка в Hills Lounge | Саларьево",
                "Мы работаем для Вас с 20:00 и до утра!",
                "https://s0.rbk.ru/v6_top_pics/media/img/4/04/346843326750044.jpg",
            ),

            Card("Новогодняя вечеринка в Hills Lounge | Румянцево",
                "Мы работаем для Вас с 20:00 и до утра!",
                "https://png.pngtree.com/thumb_back/fw800/background/20230612/pngtree-images-of-winter-and-white-background-wallpapers-free-download-image_2935697.jpg",
            ),

            Card("Новогодняя вечеринка в Hills Lounge | Солнцево",
                "Мы работаем для Вас с 20:00 и до утра!",
                "https://img1.akspic.ru/previews/5/3/0/9/7/179035/179035-voda-gora-gidroresursy-rastenie-oblako-550x310.jpg",
                )
            )

        val adapter = CustomRecyclerAdapter(cards)
        recyclerView.adapter = adapter

        startAutoScroll(recyclerView, cards.size)

        return view
    }

    private fun startAutoScroll(recyclerView: RecyclerView, itemCount: Int) {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (itemCount > 0 ) {
                    currentIndex = (currentIndex + 1) % itemCount
                    recyclerView.smoothScrollToPosition(currentIndex)
                }
                handler.postDelayed(this, 5000)
            }
        }, 5000)
    }

    // Метод для выхода из учетной записи
    private fun onLogout() {
        // Очистка данных об авторизации (например, флаг или токен)
        // Тут можно сбросить состояние авторизации (например, SharedPreferences или другую логику)
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        // Очистить все данные
        editor.clear()
        editor.apply()

        // hide BottomNavigationView
        (activity as? MainActivity)?.hideBottomNavigation()

        // open SignInFragment
        (activity as? MainActivity)?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame_layout, SignInFragment())
            ?.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        _binding = null
    }
}