package com.example.seatreservation.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.seatreservation.repository.CinemaRepository

class CinemaViewModelProviderFactory(
    val app: Application,
    val cinemaRepository: CinemaRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CinemaViewModel(app, cinemaRepository) as T
    }
}