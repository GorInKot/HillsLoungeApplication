package com.example.hillsloungeapplication.Places.Details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.hillsloungeapplication.Places.Details.RV.ServicesAdapter
import com.example.hillsloungeapplication.Places.Details.Reservating.ReservationBottomSheet
import com.example.hillsloungeapplication.R
import com.example.hillsloungeapplication.databinding.FragmentPlaceDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider


class PlaceDetailFragment : Fragment() {

    private var _binding: FragmentPlaceDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var mapView: MapView
    private val viewModel: PlacesDetailsViewModel by viewModels()
    private lateinit var servicesAdapter: ServicesAdapter
    private var telegramLink: String? = null
    private var phoneNumber: String? = null
    private var menu: String? = null

    companion object {
        private const val ARG_HEADER = "header"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_IMAGE_RES_ID = "image_res_id"
        private const val ARG_ADDRESS = "address"
        private const val ARG_WORKINGHOURS = "working_hours"
        private const val ARG_LATITUDE = "latitude"
        private const val ARG_LONGITUDE = "longitude"
        private const val ARG_TELEGRAMLINKS = "telegram_links"
        private const val ARG_PHONENUMBER = "phoneNumber"
        private const val ARG_MENU = "menu"

        // Функция для создания нового экземпляра фрагмента
        fun newInstance(
            header: String,
            description: String,
            imageResId: Int,
            address: String,
            workingHours: String,
            latitude: Double,
            longitude: Double,
            telegramLink: String,
            phoneNumber: String,
            menu: String?
        ): PlaceDetailFragment {
            val fragment = PlaceDetailFragment()
            val args = Bundle()
            args.putString(ARG_HEADER, header)
            args.putString(ARG_DESCRIPTION, description)
            args.putInt(ARG_IMAGE_RES_ID, imageResId) // Используем putInt для ресурса
            args.putString(ARG_ADDRESS, address)
            args.putString(ARG_WORKINGHOURS, workingHours)
            args.putDouble(ARG_LATITUDE, latitude)
            args.putDouble(ARG_LONGITUDE, longitude)
            args.putString(ARG_TELEGRAMLINKS, telegramLink)
            args.putString(ARG_PHONENUMBER, phoneNumber)
            args.putString(ARG_MENU, menu)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        telegramLink = arguments?.getString(ARG_TELEGRAMLINKS) // Получаем ссылку
        phoneNumber = arguments?.getString(ARG_PHONENUMBER) // Получаем номер телефона
        menu = arguments?.getString(ARG_MENU) // Получаем ссылку на меню

        val telegramSection = view.findViewById<LinearLayout>(R.id.text_telegram_section)

        // Секция "Забронировать стол"
        val reserveButton = binding.bookTableSection
        reserveButton.setOnClickListener {
            val reservationDialog = ReservationBottomSheet(telegramLink ?: "your_telegram_bot")
            reservationDialog.show(parentFragmentManager, "ReservationBottomSheet")
        }

        // Секция "Написать в телеграм"
        telegramSection.setOnClickListener {
            showTelegramDialog()
        }

        // Секция "Позвонить"
        val callSection = view.findViewById<LinearLayout>(R.id.call_section)
        callSection.setOnClickListener {
            showCallDialog()
        }

        // Секция "Меню"
        val menuSection = view.findViewById<LinearLayout>(R.id.menu_section)
        menuSection.setOnClickListener {
            showMenuDialog()
        }

        setUpRecyclerView()
        setUpObservers()

        // Получаем данные из аргументов
        val title = arguments?.getString(ARG_HEADER) ?: ""
        val address = arguments?.getString(ARG_ADDRESS) ?: ""
        val imageResId = arguments?.getInt(ARG_IMAGE_RES_ID) ?: 0 // Получаем ресурс изображения
        val description = arguments?.getString(ARG_DESCRIPTION) ?: ""
        val workingHours = arguments?.getString(ARG_WORKINGHOURS) ?: ""
        val latitude = arguments?.getDouble(ARG_LATITUDE) ?: 0.0
        val longitude = arguments?.getDouble(ARG_LONGITUDE) ?: 0.0

        // Устанавливаем данные во view
        binding.placeDetailsTitle.text = title
        binding.placeDetailsAddress.text = address
        binding.placeDetailsDescription.text = description

        val mapView = binding.placeDetailMapView
        val targetPoint = Point(latitude, longitude)
        mapView.map?.move(
            CameraPosition(Point(
                latitude, longitude),
                14.0f,
                0.0f,
                0.0f)
        )
        val mapObjects = mapView.map.mapObjects
        mapObjects.addPlacemark(
            targetPoint,
            ImageProvider.fromResource(
                requireContext(),
                R.drawable.free_icon_dating_app_2462345)
        )

        val zoomInButton = binding.zoomInButton
        val zoomOutButton = binding.zoomOutButton

        zoomInButton.setOnClickListener {
            mapView?.map?.move(
                CameraPosition(
                    mapView?.map?.cameraPosition?.target ?: Point(55.624549, 37.618423),
                    mapView?.map?.cameraPosition?.zoom?.plus(1) ?: 10.0f, 0.0f, 0.0f
                )
            )
        }

        zoomOutButton.setOnClickListener {
            mapView?.map?.move(
                CameraPosition(
                    mapView?.map?.cameraPosition?.target ?: Point(55.624549, 37.618423),
                    mapView?.map?.cameraPosition?.zoom?.minus(1) ?: 10.0f, 0.0f, 0.0f
                )
            )
        }

        val workingHoursTextView = binding.placeDetailsWorkingHours
        workingHoursTextView.text = "$workingHours"

        // Устанавливаем изображение из ресурса
        if (imageResId != 0) {
            binding.placeDetailsImage.setImageResource(imageResId)
        } else {
            Glide.with(requireContext())
                .load(imageResId)
                .placeholder(R.drawable.wfcubjudis72rza2fjhpywblwwu7rtso)
                .error(R.drawable.free_icon_dating_app_2462345)
                .into(binding.placeDetailsImage)
        }

        val closeButton = binding.closeButton
        closeButton.setOnClickListener {
            val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNavigationView.visibility = View.VISIBLE

            // Закрываем фрагмент
            parentFragmentManager.popBackStack()
        }

    }

    private fun showMenuDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Меню заведения")

        if (menu.isNullOrEmpty()) {
            // Если меню нет, показываем сообщение
            builder.setMessage("Извините, сайт еще не работает. Пожалуйста, обратитесь к сотрудникам в заведении.")
            builder.setPositiveButton("ОК") { dialog, _ ->
                dialog.dismiss()
            }
        } else {
            // Если меню есть, даем возможность открыть сайт
            builder.setMessage("Хотите посмотреть меню заведения?")
            builder.setPositiveButton("Открыть сайт") { _, _ ->
                menu?.let {
                    openMenuWebsite(it)
                }
            }
            builder.setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
        }

        val dialog = builder.create()
        dialog.show()
    }


    private fun openMenuWebsite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun showCallDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Позвонить в заведение")
        builder.setMessage("Вы хотите позвонить в это заведение?")

        builder.setPositiveButton("Позвонить") { _, _ ->
            phoneNumber?.let { makePhoneCall(it) }
        }

        builder.setNegativeButton("Отмена") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun makePhoneCall(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        startActivity(intent)
    }

    private fun showTelegramDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Подтверждение")
        builder.setMessage("Вы действительно хотите написать в Telegram?")

        builder.setPositiveButton("Написать в Telegram") { _, _ ->
            telegramLink?.let { openTelegramChat(it) }
        }

        builder.setNegativeButton("Отмена") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun openTelegramChat(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.setPackage("org.telegram.messenger") // Открывает Telegram, если установлен
        startActivity(intent)
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.placeDetailRvServices
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        servicesAdapter = ServicesAdapter(emptyList())
        recyclerView.adapter = servicesAdapter
    }

    private fun setUpObservers() {
        viewModel.cards.observe(viewLifecycleOwner) { cards ->
            servicesAdapter.updateData(cards)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.placeDetailMapView.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        binding.placeDetailMapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
