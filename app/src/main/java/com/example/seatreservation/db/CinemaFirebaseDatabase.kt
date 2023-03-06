package com.example.seatreservation.db

import com.example.seatreservation.db.firebase.MovieDaoFB
import com.example.seatreservation.db.firebase.ReservationDaoFB
import com.google.firebase.firestore.FirebaseFirestore

class CinemaFirebaseDatabase(
    dbFBInstance: FirebaseFirestore
) {
    val getMovieDaoFB = MovieDaoFB(dbFBInstance)

    val getReservationDaoFB = ReservationDaoFB(dbFBInstance)
}