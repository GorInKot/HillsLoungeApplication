package com.example.hillsloungeapplication.Settings

import android.os.Bundle
import android.provider.MediaStore.Audio.Albums
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.hillsloungeapplication.databinding.FragmentSettingsBinding
import com.example.hillsloungeapplication.retrofit.AlbumService
import com.example.hillsloungeapplication.retrofit.RetroftInstance

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        val retrofitService = RetroftInstance.getRetrofitInstance().create(AlbumService::class.java)

        val responseLiveData =
            liveData {
                val response = retrofitService.getAlbums()
                emit(response)
            }

        responseLiveData.observe(viewLifecycleOwner, Observer {
            val albumList = it.body()?.listIterator()
            if(albumList != null) {
                while(albumList.hasNext()) {
                    val albumItem = albumList.next()

                    val albumTitle = "Album Title: ${albumItem.title} \n"
                    binding.titleTV.append(albumTitle)
                }
            }
        })
        // Inflate the layout for this fragment
        return view
    }


}