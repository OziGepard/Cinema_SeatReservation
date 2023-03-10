package com.example.seatreservation.db.room

import androidx.room.*
import com.example.seatreservation.models.Movie

@Dao
interface FavoriteDaoRoom {

    @Query("SELECT * from favorite_table")
    fun getFavorite(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(movie: Movie)

    @Query("DELETE FROM favorite_table WHERE title = :movie")
    suspend fun deleteFavorite(movie: String)
}