package com.example.seatreservation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.seatreservation.R
import com.example.seatreservation.databinding.FragmentMovieSelectedBinding
import com.example.seatreservation.models.Reservation
import com.example.seatreservation.ui.CinemaActivity
import com.example.seatreservation.ui.CinemaViewModel
import com.google.android.material.snackbar.Snackbar

class MovieSelectedFragment: Fragment(R.layout.fragment_movie_selected) {

    private var _binding: FragmentMovieSelectedBinding? = null
    private val binding get() = _binding!!
    private val args: MovieSelectedFragmentArgs by navArgs()
    private val TAG = "MovieSelectedFragment"

    lateinit var viewModel: CinemaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieSelectedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val childFragment = SeatReservationFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.child_fragment_container, childFragment).commit()
    }

    override fun onStart() {
        super.onStart()
        viewModel = (activity as CinemaActivity).viewModel
        viewModel.movieTitle = args.title
        initializeUI()
    }

    private fun initializeUI() {
        binding.movieSelectedTitle.text = args.title

        val spinner = binding.timeSelectorSpinner
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.time_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                viewModel.movieTime = parent!!.getItemAtPosition(position).toString()
                Log.d(TAG, viewModel.movieTime.toString())
                viewModel.getSelectedMovieReservations()
                viewModel.clearList()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.movieTime = parent!!.getItemAtPosition(0).toString()
                Log.d(TAG, viewModel.movieTime.toString())
                viewModel.getSelectedMovieReservations()
            }
        }

        binding.movieSelectedReservationBtn.setOnClickListener {
            val name = binding.movieSelectedName.text.toString()
            val lastname = binding.movieSelectedLastname.text.toString()
            val email = binding.movieSelectedEmail.text.toString()

            if(name.isEmpty() || lastname.isEmpty() || email.isEmpty())
            {
                Snackbar.make(it, "Uzupełnij brakujące informacje!", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(viewModel.seatsSelected.isEmpty())
            {
                Snackbar.make(it, "Wybierz miejsce/miejsca do zarezerwowania!", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.addReservationRoom(name, lastname, email)
            viewModel.addReservationFB(email)
            viewModel.clearList()

        }
    }
}