package com.example.hillsloungeapplication.Places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hillsloungeapplication.Places.Details.PlaceDetailFragment
import com.example.hillsloungeapplication.R
import com.example.hillsloungeapplication.databinding.FragmentPlacesBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class PlacesFragment : Fragment() {

    private var _binding: FragmentPlacesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PlaceAdapter
    private val viewModel: PlacesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        val rv = binding.rvPlaces
        rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapter = PlaceAdapter(emptyList()) { selectedPlace ->
            // Получаем фрагмент с деталями места через ViewModel
            val placeDetailFragment = viewModel.getPlaceDetails(selectedPlace)

            // Скрываем BottomNavigationView
            val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNavigationView.visibility = View.GONE

            // Переходим на PlaceDetailFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, placeDetailFragment)
                .addToBackStack(null)
                .commit()
        }
        rv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.places.observe(viewLifecycleOwner) { places ->
            adapter.updateData(places)
        }
    }
}
