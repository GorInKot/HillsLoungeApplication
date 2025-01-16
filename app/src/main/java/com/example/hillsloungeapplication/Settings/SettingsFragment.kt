package com.example.hillsloungeapplication.Settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hillsloungeapplication.R
import com.example.hillsloungeapplication.databinding.FragmentSettingsBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

class SettingsFragment : Fragment() {

//    private val TAG: String = "CHECK_RESPONSE"

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private  var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        mapView = binding.mapview

        mapView?.map?.move(
            CameraPosition(Point(
                55.62416320205327, 37.41224507131568),
                14.0f,
                0.0f,
                0.0f))

        val imageProvider = ImageProvider.fromResource(requireContext(), R.drawable.free_icon_dating_app_2462345)
        val placemarkSal = mapView?.mapWindow?.map?.mapObjects?.addPlacemark()?.apply {
            geometry = Point(55.617211635322, 37.4087668112054)
            setIcon(imageProvider)
        }
        val placemarkRum = mapView?.mapWindow?.map?.mapObjects?.addPlacemark()?.apply {
            geometry = Point(55.63390917549483,37.41457944422558)
            setIcon(imageProvider)
        }
        val placemarkSol = mapView?.mapWindow?.map?.mapObjects?.addPlacemark()?.apply {
            geometry = Point(55.64359602205373,37.393294931877165)
            setIcon(imageProvider)
        }
        // [55.617211635322,37.4087668112054] - саларьево
        // [55.63390917549483,37.41457944422558] - румянцево
        // [55.64359602205373,37.393294931877165] - солнцево

        placemarkSal?.addTapListener(placemarkTapListener)
        placemarkRum?.addTapListener(placemarkTapListener)
        placemarkSol?.addTapListener(placemarkTapListener)

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
//
//        mapView?.map?.move(
//            CameraPosition(Point(55.624549, 37.421171),
//                14.0f,
//                0.0f,
//                0.0f) // Москва
//        )
//
//        // добавление маркера
//        val mapObjects = mapView?.map?.mapObjects
//        val point = Point(56.616880, 37.407304)
//        Log.d("SettingsFragment", "Добавляю метку: ${point.latitude}, ${point.longitude}")
//        mapObjects?.addPlacemark(
//            point,
//            ImageProvider.fromResource(requireContext(), android.R.drawable.ic_menu_mylocation))
//        mapObjects?.addPlacemark(point, ImageProvider.fromResource(requireContext(), R.drawable.free_icon_dating_app_2462345))

//        val points = listOf(
//            Point(56.616880, 37.407304),
//            Point(56.633856, 37.414059),
//            Point(56.642977, 37.393074)
//        )
//
//        for (point in points) {
//            mapObjects?.addPlacemark(
//                point,
//                ImageProvider.fromResource(requireContext(), R.drawable._im_702502b5_69ff_4c16_9148_80fedb196c89)
//            )
//        }
//
        // retrofit code
        // [ start ]
//        val retrofitService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)
//
//        val responseLiveData =
//            liveData {
//                val response = retrofitService.getAlbums()
//                emit(response)
//            }
//
//        responseLiveData.observe(viewLifecycleOwner, Observer {
//            val albumList = it.body()?.listIterator()
//            if(albumList != null) {
//                while(albumList.hasNext()) {
//                    val albumItem = albumList.next()
//                    Log.d(TAG, "onResponse: ${albumItem.title}")
//
//                    val albumTitle = "Album Title: ${albumItem.title} \n"
//                    binding.titleTV.append(albumTitle)
//                }
//            }
//        })
        // [ end ]


        // Inflate the layout for this fragment
        return view
    }
    private val placemarkTapListener = MapObjectTapListener {_, point ->
        Toast.makeText(
            requireContext(),
            "Tapped the point (${point.longitude}, ${point.latitude})",
            Toast.LENGTH_SHORT
        ).show()
        true
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onDestroyView() {
        mapView = null
        super.onDestroyView()
    }
}