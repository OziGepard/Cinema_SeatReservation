package com.example.seatreservation.db.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.seatreservation.models.Movie

@Dao
interface FavoriteDaoRoom {

    @Query("SELECT * from favorite_table")
    fun getFavorite(): List<Movie>

    @Insert
    suspend fun addFavorite(movie: Movie)

    @Delete
    suspend fun deleteFavorite(movie: Movie)
}