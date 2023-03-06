package com.example.seatreservation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.seatreservation.R
import com.example.seatreservation.databinding.ActivityCinemaBinding
import com.example.seatreservation.db.CinemaFirebaseDatabase
import com.example.seatreservation.db.CinemaRoomDatabase
import com.example.seatreservation.repository.CinemaRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CinemaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCinemaBinding
    val viewModel: CinemaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCinemaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.moviesNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)


    }
}