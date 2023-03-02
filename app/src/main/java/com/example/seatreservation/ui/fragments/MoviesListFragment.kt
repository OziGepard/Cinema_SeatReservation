package com.example.seatreservation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seatreservation.R
import com.example.seatreservation.adapters.MoviesAdapter
import com.example.seatreservation.databinding.FragmentMoviesListBinding
import com.example.seatreservation.models.Movie
import com.example.seatreservation.ui.CinemaActivity
import com.example.seatreservation.ui.CinemaViewModel

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    private val TAG = "MoviesListFragment"

    lateinit var viewModel: CinemaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel = (activity as CinemaActivity).viewModel
        setupRecyclerview()
    }

    private fun setupRecyclerview() {
        val mAdapter = MoviesAdapter(this::changeFragment)
        binding.moviesRecyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false)
            setHasFixedSize(false)
            registerForContextMenu(this)
        }
        viewModel.moviesList.observe(this) {listOfMovies ->
            mAdapter.updateList(listOfMovies)
        }


    }

    private fun changeFragment(title: String, movieSelected: Movie)
    {
        viewModel.setSelectedMovie(movieSelected)
        val action = MoviesListFragmentDirections.actionMoviesListFragmentToMovieSelectedFragment(title)
        findNavController().navigate(action)
    }
}