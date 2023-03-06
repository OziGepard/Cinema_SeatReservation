package com.example.seatreservation.util

import android.content.Context
import androidx.room.Room
import com.example.seatreservation.db.CinemaFirebaseDatabase
import com.example.seatreservation.db.CinemaRoomDatabase
import com.example.seatreservation.repository.CinemaRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext appContext: Context):
            CinemaRoomDatabase
    {
        return Room.databaseBuilder(
            appContext.applicationContext,
            CinemaRoomDatabase::class.java,
            "reservation_db.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFirebaseDatabase(dbFBInstance: FirebaseFirestore): CinemaFirebaseDatabase{
        return CinemaFirebaseDatabase(dbFBInstance)
    }

    @Singleton
    @Provides
    fun provideFirebaseInstance(): FirebaseFirestore
    {
        return FirebaseFirestore.getInstance()
    }
}