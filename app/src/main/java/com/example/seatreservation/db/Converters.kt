package com.example.seatreservation.db

import androidx.room.TypeConverter
import com.example.seatreservation.models.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromMovie(movie: Movie): String
    {
        return Gson().toJson(movie)
    }

    @TypeConverter
    fun toMovie(json: String): Movie
    {
        return Gson().fromJson(json, Movie::class.java)
    }
}