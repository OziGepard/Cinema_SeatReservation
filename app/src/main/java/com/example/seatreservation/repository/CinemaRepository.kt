package com.example.seatreservation.repository

import com.example.seatreservation.db.CinemaFirebaseDatabase
import com.example.seatreservation.db.CinemaRoomDatabase
import com.example.seatreservation.db.firebase.callbacks.ReservationsSnapshotCallback
import com.example.seatreservation.models.Movie
import com.example.seatreservation.models.Reservation

class CinemaRepository(
    private val dbRoom: CinemaRoomDatabase,
    private val dbFB: CinemaFirebaseDatabase
) {

    suspend fun getMovies() = dbFB.getMovieDaoFB.getMovies()

    fun getFavorite() = dbRoom.getFavoriteDaoRoom().getFavorite()

    fun getSelectedMovieReservations(
        title: String,
        time: String,
        callback: ReservationsSnapshotCallback) = dbFB.getReservationDaoFB.getSelectedMovieReservations(title, time, callback)

    fun unsubscribeListener() = dbFB.getReservationDaoFB.unsubscribeListener()

    fun addReservationFB(title: String, time: String, email: String, seats: List<Int>) =
        dbFB.getReservationDaoFB.addReservationFB(title, time, email, seats)

    suspend fun addReservationRoom(reservation: Reservation) =
        dbRoom.getReservationDaoRoom().addReservationRoom(reservation)

    fun getReservation() = dbRoom.getReservationDaoRoom().getReservations()
}