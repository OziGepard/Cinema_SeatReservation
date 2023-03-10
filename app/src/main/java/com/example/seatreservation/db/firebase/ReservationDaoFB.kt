package com.example.seatreservation.db.firebase

import android.util.Log
import com.example.seatreservation.db.firebase.callbacks.ReservationsSnapshotCallback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class ReservationDaoFB @Inject constructor(private val db: FirebaseFirestore) {

    private val TAG = "ReservationDaoFB"

    private lateinit var subscription: ListenerRegistration


    fun getSelectedMovieReservations(title: String, time: String, callback: ReservationsSnapshotCallback)
    {
        subscription = db.collection("reservations")
            .document(title)
            .collection(time)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    for (document in snapshot.documents) {
                        if (document.data?.isEmpty() == true) {
                            callback.onReservationSnapshotCallback(null)
                            return@addSnapshotListener
                        }
                    }
                    callback.onReservationSnapshotCallback(snapshot)
                } else {
                    Log.d(TAG, "Current data: null")
                }
            }
    }

    fun addReservationFB(title: String, time: String, email: String, seats: List<Int>)
    {
        val data = hashMapOf(
            "e-mail" to email,
            "seat_number" to seats
        )

        db.collection("reservations")
            .document(title)
            .collection(time)
            .add(data)
            .addOnSuccessListener {
                //TODO- Callback z potwierdzeniem o rezerwacji
            }
            .addOnFailureListener {
                //TODO - Callback z niepowodzeniem rezerwacji
            }
    }

    //TODO - Dodać onBackPressed wewnątrz fragmentu a w nim
    // unsubsribeListener() i clearList() z CinemaViewModel
    fun unsubscribeListener()
    {
        subscription.remove()
    }
}