package com.example.seatreservation.db.firebase.callbacks

import com.google.firebase.firestore.QuerySnapshot

interface ReservationsSnapshotCallback {
    fun onReservationSnapshotCallback(snapshot: QuerySnapshot?)
}