package com.example.seatreservation.models
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val price: Int = 0,
    val age: Int = 0,
)