package com.example.seatreservation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.seatreservation.R
import com.example.seatreservation.databinding.FragmentMovieSelectedInfoBinding
import com.example.seatreservation.ui.CinemaActivity
import com.example.seatreservation.ui.CinemaViewModel

class MovieSelectedInfoFragment: Fragment(R.layout.fragment_movie_selected_info) {

    private var _binding: FragmentMovieSelectedInfoBinding? = null
    private val binding get() = _binding!!
    private val args: MovieSelectedInfoFragmentArgs by navArgs()
    private val TAG = "MovieSelectedInfoFragment"

    lateinit var viewModel: CinemaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieSelectedInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel = (activity as CinemaActivity).viewModel
        viewModel.movieTitle = args.title

        initializeUI()
    }

    private fun initializeUI() {
        binding.movieSelectedInfoReservationBtn.setOnClickListener {
            changeFragment()
        }
    }

    private fun changeFragment() {
        val action = MovieSelectedInfoFragmentDirections.actionMovieSelectedInfoFragmentToMovieSelectedReservationFragment()
        findNavController().navigate(action)

    }


}