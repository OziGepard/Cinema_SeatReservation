package com.example.seatreservation.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.seatreservation.db.room.FavoriteDaoRoom
import com.example.seatreservation.db.room.ReservationDaoRoom
import com.example.seatreservation.models.Reservation
import com.example.seatreservation.models.Movie
@Database(
    entities = [Movie::class, Reservation::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CinemaRoomDatabase: RoomDatabase() {

    abstract fun getFavoriteDaoRoom(): FavoriteDaoRoom
    abstract fun getReservationDaoRoom(): ReservationDaoRoom
}