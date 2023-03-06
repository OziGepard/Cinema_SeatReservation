package com.example.seatreservation.db.firebase

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

class MovieDaoFB @Inject constructor(private val db: FirebaseFirestore){
    val TAG = "MovieDaoFB"


    suspend fun getMovies(): List<DocumentSnapshot> {
        val snapshot = db.collection("movies").get().await()
        return snapshot.documents
    }

}