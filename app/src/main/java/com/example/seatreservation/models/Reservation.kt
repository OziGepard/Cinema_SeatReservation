package com.example.seatreservation.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservation_table")
data class Reservation(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val lastname: String,
    val email: String,
    val movie: Movie
)
