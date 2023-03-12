package com.example.seatreservation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.seatreservation.R
import com.example.seatreservation.databinding.FragmentMovieSelectedReservationBinding
import com.example.seatreservation.ui.CinemaActivity
import com.example.seatreservation.ui.CinemaViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MovieSelectedReservationFragment: Fragment(R.layout.fragment_movie_selected_reservation) {

    private var _binding: FragmentMovieSelectedReservationBinding? = null
    private val binding get() = _binding!!
    private val TAG = "MovieSelectedReservationFragment"

    lateinit var viewModel: CinemaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieSelectedReservationBinding.inflate(inflater, container, false)
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
        initializeUI()
    }

    private fun initializeUI() {
        var isFavorite = false

        binding.movieSelectedTitle.text = viewModel.movieTitle

        //-----Favorite button setImage-----

        lifecycleScope.launch{
            viewModel.isOnTheFavoriteList()
        }

        viewModel.isMovieFavorite.observe(viewLifecycleOwner) {status ->
            isFavorite = if (status == true) {
                binding.addToFavBtn.setImageResource(R.drawable.baseline_favorite_24)
                true
            } else {
                binding.addToFavBtn.setImageResource(R.drawable.baseline_favorite_border_24)
                false
            }
        }

        //----------------------------------

        //-----ArrayAdapter for spinner------

        val spinner = binding.timeSelectorSpinner
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.time_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        //---------------------------------------

        //------Spinner Listener---------

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
        //------------------------------------

        //-------Reservation Button-----------

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
        //-------------------------------------

        //------Favorite Button----------

        binding.addToFavBtn.setOnClickListener{
            if(isFavorite)
            {
                //Log.d(TAG, "Movie Delete")
                viewModel.deleteMovieFromFavorite()
                binding.addToFavBtn.setImageResource(R.drawable.baseline_favorite_border_24)
            }
            else {
                //Log.d(TAG, "Movie Add")
                viewModel.addMovieToFavorite()
                binding.addToFavBtn.setImageResource(R.drawable.baseline_favorite_24)
            }
            viewModel.setMovieFavoriteStatus(!isFavorite)
        }

        //-------------------------------
    }
}