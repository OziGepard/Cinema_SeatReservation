package com.example.seatreservation.db.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.seatreservation.models.Reservation

@Dao
interface ReservationDaoRoom {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReservationRoom(reservation: Reservation)

    @Delete
    suspend fun deleteReservation(reservation: Reservation)

    @Query("SELECT * FROM reservation_table")
    fun getReservations(): LiveData<List<Reservation>>
}