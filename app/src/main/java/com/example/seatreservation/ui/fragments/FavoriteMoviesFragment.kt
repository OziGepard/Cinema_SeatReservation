package com.example.seatreservation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seatreservation.R
import com.example.seatreservation.adapters.FavoriteAdapter
import com.example.seatreservation.adapters.MoviesAdapter
import com.example.seatreservation.databinding.FragmentFavoriteMoviesBinding
import com.example.seatreservation.ui.CinemaActivity
import com.example.seatreservation.ui.CinemaViewModel

class FavoriteMoviesFragment: Fragment(R.layout.fragment_favorite_movies) {

    private var _binding: FragmentFavoriteMoviesBinding? = null
    private val binding get() = _binding!!
    private val TAG = "FavoriteMoviesFragment"

    lateinit var viewModel: CinemaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel = (activity as CinemaActivity).viewModel

        setupRecyclerview()

    }

    private fun setupRecyclerview() {
        val mAdapter = FavoriteAdapter()
        binding.favoriteRecyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false)
            setHasFixedSize(false)
            registerForContextMenu(this)
        }
        viewModel.getFavorite().observe(this) {listOfFavorites ->
            mAdapter.updateList(listOfFavorites)
        }


    }
}