package com.example.basic_try

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [UserDataEnt::class,UserPersonalDetailsEnt::class], version = 1, exportSchema = false)
public abstract class UserRoomDatabase: RoomDatabase() {

    abstract fun userDao():UserDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        //Volatile annotation- whenever any change is made to this variable(here INSTANCE)
        //they will be immediately visible to other threads
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        fun getDatabase(context: Context): UserRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            //below 'this' is of UserRoomDatabase
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserRoomDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}