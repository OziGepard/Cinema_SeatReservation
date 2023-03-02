package com.example.seatreservation.db.firebase

import android.util.Log
import com.example.seatreservation.models.Movie
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class MovieDaoFB{

    private val db = Firebase.firestore
    val TAG = "MovieDaoFB"


    suspend fun getMovies(): List<DocumentSnapshot> {
//        db.collection("movies")
//            .addSnapshotListener{snapshot, e ->
//                if (e != null) {
//                    Log.w(TAG, "Listen failed.", e)
//                    return@addSnapshotListener
//                }
//
//                if (snapshot != null && !snapshot.isEmpty) {
//                    Log.d(TAG, "Current data: ${snapshot.toObjects(Movie::class.java)}")
//                } else {
//                    Log.d(TAG, "Current data: null")
//                }
//            }
        val snapshot = db.collection("movies").get().await()
        return snapshot.documents
    }

}