package com.example.hillsloungeapplication.Home

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hillsloungeapplication.Home.recyclerView.BigRV.Card
import com.example.hillsloungeapplication.Home.recyclerView.BigRV.CustomRecyclerAdapter
import com.example.hillsloungeapplication.Home.recyclerView.SmallRV.CustomRecyclerViewAdapter
import com.example.hillsloungeapplication.MainActivity
import com.example.hillsloungeapplication.R
import com.example.hillsloungeapplication.auth.signIn.SignInFragment
import com.example.hillsloungeapplication.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CustomRecyclerAdapter
    private lateinit var smallAdapter: CustomRecyclerViewAdapter
    private var currentIndexBRV = 0
    private var currentIndexSRV = 0
    private val handler_big = Handler(Looper.getMainLooper())
    private val handler_small = Handler(Looper.getMainLooper())


    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerViewB = binding.homeFragmentRv
        recyclerViewB.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val rvSmall = binding.homeFragmentSmallRv
        rvSmall.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)

        binding.fragmentHomeImageButton.setOnClickListener {
            onLogout()
        }

        // Big RecyclerView
        val cards_BRV = listOf(
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

        val cardsSRV = listOf(
            R.drawable._im_702502b5_69ff_4c16_9148_80fedb196c89,
            R.drawable.wfcubjudis72rza2fjhpywblwwu7rtso,
            R.drawable._im_702502b5_69ff_4c16_9148_80fedb196c89,
            R.drawable.wfcubjudis72rza2fjhpywblwwu7rtso,
            R.drawable._im_702502b5_69ff_4c16_9148_80fedb196c89,
            R.drawable.wfcubjudis72rza2fjhpywblwwu7rtso,
            R.drawable._im_702502b5_69ff_4c16_9148_80fedb196c89,
            R.drawable.wfcubjudis72rza2fjhpywblwwu7rtso,
            R.drawable._im_702502b5_69ff_4c16_9148_80fedb196c89,
            R.drawable.wfcubjudis72rza2fjhpywblwwu7rtso,
            R.drawable._im_702502b5_69ff_4c16_9148_80fedb196c89,
            R.drawable.wfcubjudis72rza2fjhpywblwwu7rtso
        )

        // TODO - починить эту неработающую хуету!!!
        adapter = CustomRecyclerAdapter(cards_BRV) { selectedCard ->
            Log.d("HomeFragment", "Card clicked: ${selectedCard.header}")
            val bottomSheet = CardDetailsBottomSheetFragment.newInstance(
                selectedCard.header,
                selectedCard.helperText,
                selectedCard.imageUrl
            )
            bottomSheet.show(parentFragmentManager, "CardDetailsBottomSheet")
        }

//        recyclerViewB.adapter = adapter

        recyclerViewB.adapter = adapter
        startBigAutoScroll(recyclerViewB, cards_BRV.size)

        // Small RecyclerView
        smallAdapter = CustomRecyclerViewAdapter(cardsSRV)
        rvSmall.adapter = smallAdapter

        // Добавление отступов
        val spaceInDp = 6
        val spaceInPx = dpToPx(spaceInDp)
        val itemDecoration = SpacesItemDecoration(spaceInPx)
        rvSmall.addItemDecoration(itemDecoration)

        startSmallAutoScroll(rvSmall, cardsSRV.size)


        return view
    }

    private fun startBigAutoScroll(recyclerView: RecyclerView, itemCount: Int) {
        handler_big.postDelayed(object : Runnable {
            override fun run() {
                if (itemCount > 0 ) {
                    currentIndexBRV = (currentIndexBRV + 1) % itemCount
                    recyclerView.smoothScrollToPosition(currentIndexBRV)
                }
                handler_big.postDelayed(this, 5000)
            }
        }, 5000)
    }

    private fun startSmallAutoScroll(recyclerView: RecyclerView, itemCount: Int) {
        handler_small.postDelayed(object : Runnable {
            override fun run() {
                if (itemCount > 0 ) {
                    currentIndexSRV = (currentIndexSRV + 1) % itemCount
                    recyclerView.smoothScrollToPosition(currentIndexSRV)
                }
                handler_small.postDelayed(this, 4500)
            }
        }, 4500)
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

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler_big.removeCallbacksAndMessages(null)
        handler_small.removeCallbacksAndMessages(null)
        _binding = null
    }
}

// Класс для отступов
class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position == 0) {
            outRect.set(space, 0, space, 0) // Устанавливаем отступы: слева и справа
        } else {
            outRect.set(0, 0, space, 0) // Устанавливаем только правый отступ
        }
    }
}


// TODO - почему не видно картики в маленьком RV
/// todo - ориентация не верная у маленького RV
