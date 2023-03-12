package com.example.seatreservation.ui.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableRow
import androidx.fragment.app.Fragment
import com.example.seatreservation.R
import com.example.seatreservation.databinding.FragmentSeatReservationBinding
import com.example.seatreservation.ui.CinemaActivity
import com.example.seatreservation.ui.CinemaViewModel
import com.google.android.material.snackbar.Snackbar

class SeatReservationFragment: Fragment(R.layout.fragment_seat_reservation) {

    private var _binding: FragmentSeatReservationBinding? = null
    private val binding get() = _binding!!
    private val TAG = "SeatReservationFragment"

    private val listOfSeats = mutableListOf<Button>()

    lateinit var viewModel: CinemaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeatReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeTable()
    }

    private fun initializeTable() {
        val tableOfSeats = binding.tableSeats

        val inflater: LayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        for(row in 0..2)
        {
            val currentTableRow = tableOfSeats.getChildAt(row) as TableRow

            for(column in 0..6)
            {
                val newSeat = inflater.inflate(R.layout.single_seat, currentTableRow, false) as Button
                newSeat.tag = listOfSeats.size

                newSeat.setOnClickListener {
                    val index = it.tag as Int
//                    Log.d(TAG, index.toString())
                    viewModel.seatsAvailableForReservation[index] = !viewModel.seatsAvailableForReservation[index]

                    if(viewModel.seatsAvailableForReservation[index])
                    {
                        Log.d(TAG, "Rezerwuje siedzenie: $index")
                        it.setBackgroundColor(Color.BLUE)
                        viewModel.seatsSelected.add(index)
                    }
                    else
                    {
                        Log.d(TAG, "Odrezerwuje siedzenie: $index")
                        it.setBackgroundColor(Color.GREEN)
                        viewModel.seatsSelected.remove(index)
                    }
                }
                listOfSeats.add(newSeat)

                currentTableRow.addView(newSeat)
            }

        }
    }

    override fun onStart() {
        super.onStart()
        viewModel = (activity as CinemaActivity).viewModel

        viewModel.reservationsList.observe(viewLifecycleOwner){

            Log.d(TAG, "Lista rezerwacji: $it")

            // Set every seat on GREEN (Reset)
            for(seat in listOfSeats)
            {
                seat.apply {
                    setBackgroundColor(Color.GREEN)
                    isClickable = true
                }
            }
            // Now set reserved seats on RED
            val seatToDrop = mutableListOf<Int>()
            var isSomeoneElseReserved = false
            for(seatReserved in it)
            {
                listOfSeats.get(seatReserved).apply {
                    setBackgroundColor(Color.RED)
                    isClickable = false
                }
                /**
                 * The case where someone else has
                 * already reserved the seat we selected earlier
                 */

                if(viewModel.seatsSelected.contains(seatReserved))
                {
                    seatToDrop.add(seatReserved)
                    viewModel.seatsSelected.remove(seatReserved)
                    isSomeoneElseReserved = true
                }
            }
            if(isSomeoneElseReserved)
            Snackbar.make(this.requireView(),
                "Miejsce numer $seatToDrop zostało właśnie zarezerwowane przez kogoś innego!",
                Snackbar.LENGTH_LONG).show()
        }
    }
}