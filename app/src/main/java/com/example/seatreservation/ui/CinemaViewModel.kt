package com.example.seatreservation.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.seatreservation.db.firebase.callbacks.ReservationsSnapshotCallback
import com.example.seatreservation.models.Movie
import com.example.seatreservation.models.Reservation
import com.example.seatreservation.repository.CinemaRepository
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CinemaViewModel @Inject constructor(
    app: Application,
    private val cinemaRepository: CinemaRepository
) : AndroidViewModel(app) {

    val TAG = "CinemaViewModel"

    private val _moviesList: MutableLiveData<MutableList<Movie>> = MutableLiveData()
    val moviesList: LiveData<MutableList<Movie>> = _moviesList                              //Firebase

    private val _reservationsList: MutableLiveData<List<Int>> = MutableLiveData()   //Firebase
    val reservationsList: LiveData<List<Int>> = _reservationsList

    private val _favoriteMoviesList: MutableLiveData<List<Movie>> = MutableLiveData()       //Room
    val favoriteMoviesList: MutableLiveData<List<Movie>> = _favoriteMoviesList

    private var movieSelected: Movie? = null

    var seatsAvailableForReservation = MutableList(21) {false}

    var seatsSelected: MutableList<Int> = mutableListOf()

    var movieTitle: String? = null
    var movieTime: String? = null

init {
    viewModelScope.launch {
        getMovies()
        // JAKIS KOMENTARZ

    }
}

    private suspend fun getMovies(){
        val documentSnapshotList = cinemaRepository.getMovies()
        val tempList = mutableListOf<Movie>()
        for(document in documentSnapshotList)
        {
            tempList.add(document.toObject(Movie::class.java)!!)
        }
        _moviesList.postValue(tempList)
    }

    fun addReservationRoom(name: String, lastname: String, email: String)
    {
        viewModelScope.launch {
            val reservation = Reservation(null, name, lastname, email, movieSelected!!)
            cinemaRepository.addReservationRoom(reservation)
        }

    }

    fun addReservationFB(email: String) =
            cinemaRepository.addReservationFB(movieTitle!!, movieTime!!, email, seatsSelected)

    fun getReservation() = cinemaRepository.getReservation()


    fun getFavorite()
    {
        _favoriteMoviesList.postValue(cinemaRepository.getFavorite())
    }

    fun getSelectedMovieReservations()
    {
        viewModelScope.launch {
            val tempList = mutableListOf<Int>()
            cinemaRepository.getSelectedMovieReservations(
                title = movieTitle!!,
                time = movieTime!!,
                callback = object: ReservationsSnapshotCallback{
                    override fun onReservationSnapshotCallback(snapshot: QuerySnapshot?) {
                        if(snapshot == null)
                        {
                            _reservationsList.postValue(listOf())
                        }
                        else
                        {
                            for(document in snapshot.documents)
                            {
                                tempList += document.get("seat_number") as List<Int>
                            }
                        }

                        _reservationsList.postValue(tempList)
                        Log.d(TAG, _reservationsList.value.toString())
                    }
                })
        }
    }

    fun setSelectedMovie(movie: Movie){
        movieSelected = movie
    }

    fun unsubscribeListener() = cinemaRepository.unsubscribeListener()

    fun clearList() {
        seatsAvailableForReservation = MutableList(21) {false}
        seatsSelected = mutableListOf()
    }




}