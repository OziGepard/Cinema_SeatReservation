package com.example.seatreservation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seatreservation.R
import com.example.seatreservation.adapters.ReservedAdapter
import com.example.seatreservation.databinding.FragmentReservedMoviesBinding
import com.example.seatreservation.ui.CinemaActivity
import com.example.seatreservation.ui.CinemaViewModel

class ReservedMoviesFragment: Fragment(R.layout.fragment_reserved_movies) {

    private var _binding: FragmentReservedMoviesBinding? = null
    private val binding get() = _binding!!
    private val TAG = "ReservedMoviesFragment"

    lateinit var viewModel: CinemaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservedMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel = (activity as CinemaActivity).viewModel

        setupRecyclerview()

    }

    private fun setupRecyclerview() {
        val mAdapter = ReservedAdapter()
        binding.reservedRecyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false)
            setHasFixedSize(false)
            registerForContextMenu(this)
        }
        viewModel.getReservation().observe(this) {listOfReservations ->
            mAdapter.updateList(listOfReservations)
        }


    }
}