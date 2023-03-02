package com.example.seatreservation.db

import android.content.Context
import com.example.seatreservation.db.firebase.MovieDaoFB
import com.example.seatreservation.db.firebase.ReservationDaoFB

class CinemaFirebaseDatabase {
    val getMovieDaoFB = MovieDaoFB()

    val getReservationDaoFB = ReservationDaoFB()

    companion object{
        @Volatile private var instance : CinemaFirebaseDatabase? = null

        fun getInstance() =
            instance ?: synchronized(this){
                instance ?: CinemaFirebaseDatabase().also { instance = it }
            }
    }
}