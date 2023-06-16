package com.example.mynotesapp.Database

import android.icu.text.CaseMap.Title
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mynotesapp.Model.Notes


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note :Notes)

    @Delete
    suspend fun delete(note :Notes)

    @Query("select * from notes_table order by id ASC")
    fun getAllNotes() : LiveData<List<Notes>>

    @Query("UPDATE notes_table set title = :title , note = :note where id = :id")
    suspend fun update(id :Int? ,title: String? , note : String?)

}