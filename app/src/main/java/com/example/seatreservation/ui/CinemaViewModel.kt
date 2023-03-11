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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val _isMovieFavorite: MutableLiveData<Boolean?> = MutableLiveData()
    val isMovieFavorite: LiveData<Boolean?> = _isMovieFavorite

    private var movieSelected: Movie? = null

    var seatsAvailableForReservation = MutableList(21) {false}

    var seatsSelected: MutableList<Int> = mutableListOf()

    var movieTitle: String? = null
    var movieTime: String? = null

init {
    viewModelScope.launch {
        getMovies()
    }
}

    private suspend fun getMovies(){
        val documentSnapshotList = cinemaRepository.getMovies()
        val tempListOfMovies = mutableListOf<Movie>()
        for(document in documentSnapshotList)
        {
            tempListOfMovies.add(document.toObject(Movie::class.java)!!)
        }
        _moviesList.postValue(tempListOfMovies)
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

    fun getFavorite() = cinemaRepository.getFavorite()

    private fun getFavoriteList() = cinemaRepository.getFavoriteList()

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
                        //Log.d(TAG, _reservationsList.value.toString())
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

    suspend fun isOnTheFavoriteList() = withContext(Dispatchers.IO) {
        val result = getFavoriteList()
            .any{ movie ->
                //Log.d(TAG, "Movie from list: $movie")
                movie.title == movieTitle
            }
        //Log.d(TAG, "Result: $result \nMovie Title: $movieTitle ")
        _isMovieFavorite.postValue(result)
    }

    fun setMovieFavoriteStatus(status: Boolean){
        _isMovieFavorite.postValue(status)
    }

    fun deleteMovieFromFavorite() {
        viewModelScope.launch {
            movieTitle?.let {
                Log.d(TAG, movieSelected.toString())
                cinemaRepository.deleteMovieFromFavorite(it)
            }
        }
    }

    fun addMovieToFavorite() {
        viewModelScope.launch {
            movieSelected?.let {
                Log.d(TAG, movieSelected.toString())
                cinemaRepository.addMovieToFavorite(it)
            }
        }
    }

}