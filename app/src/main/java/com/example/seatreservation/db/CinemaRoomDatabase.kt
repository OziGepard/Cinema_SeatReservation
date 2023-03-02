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
    version = 1
)
@TypeConverters(Converters::class)
abstract class CinemaRoomDatabase: RoomDatabase() {

    abstract fun getFavoriteDaoRoom(): FavoriteDaoRoom

    abstract fun getReservationDaoRoom(): ReservationDaoRoom

    companion object{
        @Volatile
        private var instance : CinemaRoomDatabase? = null

        operator fun invoke(context: Context) =
            instance ?: synchronized(this){
                instance ?: createDatabase(context).also { instance = it }
            }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CinemaRoomDatabase::class.java,
                "reservation_db.db"
            ).build()
    }
}