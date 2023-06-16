package com.example.mynotesapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynotesapp.Model.Notes
import com.example.mynotesapp.Utils.DATABASE_NAME
import java.security.AccessControlContext


@Database(entities = arrayOf(Notes::class), version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao() :NoteDao

    companion object{  // singleton class

        @Volatile
        private var INSTANCE :NoteDatabase? = null

        fun getDatabase(context : Context) : NoteDatabase{

            return INSTANCE ?: synchronized(this){

                val instance =  Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE= instance
                instance

            }

        }


    }


}