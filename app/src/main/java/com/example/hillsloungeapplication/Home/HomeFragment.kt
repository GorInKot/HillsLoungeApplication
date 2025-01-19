package com.example.hillsloungeapplication.Home

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        setupObservers()

        binding.fragmentHomeImageButton.setOnClickListener {
            viewModel.logout()
            navigateToSignIn()
        }

        viewModel.startAutoScrollBRV()
        viewModel.startAutoScrollSRV()
    }

    private fun setupRecyclerViews() {
        // Big RecyclerView
        val recyclerViewB = binding.homeFragmentRv
        recyclerViewB.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        adapter = CustomRecyclerAdapter(emptyList()) { selectedCard ->
            val bottomSheet = CardDetailsBottomSheetFragment.newInstance(
                selectedCard.header,
                selectedCard.helperText,
                selectedCard.imageUrl
            )
            bottomSheet.show(parentFragmentManager, "CardDetailsBottomSheet")
        }
        recyclerViewB.adapter = adapter

        // Small RecyclerView
        val rvSmall = binding.homeFragmentSmallRv
        rvSmall.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        smallAdapter = CustomRecyclerViewAdapter(emptyList())
        rvSmall.adapter = smallAdapter

        // Adding spaces between items
        val spaceInDp = 6
        val spaceInPx = dpToPx(spaceInDp)
        val itemDecoration = SpacesItemDecoration(spaceInPx)
        rvSmall.addItemDecoration(itemDecoration)
    }

    private fun setupObservers() {
        // Observing Big RecyclerView data
        viewModel.cardsBRV.observe(viewLifecycleOwner) { cards ->
            adapter.updateData(cards)
        }

        // Observing Small RecyclerView data
        viewModel.cardsSRV.observe(viewLifecycleOwner) { cards ->
            smallAdapter.updateData(cards)
        }

        // Observing auto-scroll for Big RecyclerView
        viewModel.currentIndexBRV.observe(viewLifecycleOwner) { index ->
            binding.homeFragmentRv.smoothScrollToPosition(index)
        }

        // Observing auto-scroll for Small RecyclerView
        viewModel.currentIndexSRV.observe(viewLifecycleOwner) { index ->
            binding.homeFragmentSmallRv.smoothScrollToPosition(index)
        }
    }

    private fun navigateToSignIn() {
        (activity as? MainActivity)?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame_layout, SignInFragment())
            ?.commit()
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// SpacesItemDecoration class for adding spacing between RecyclerView items
class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position == 0) {
            outRect.set(space, 0, space, 0) // Add spacing to both sides for the first item
        } else {
            outRect.set(0, 0, space, 0) // Add spacing only to the right for other items
        }
    }
}
