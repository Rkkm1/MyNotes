package com.example.mynotesapp.Database

import androidx.lifecycle.LiveData
import com.example.mynotesapp.Model.Notes

class NoteRepository(private val noteDao: NoteDao) {


        val allNotes : LiveData<List<Notes>> = noteDao.getAllNotes()

     suspend fun insert(note: Notes){
         noteDao.insert(note)
     }

    suspend fun delete(note : Notes){
        noteDao.delete(note)
    }

    suspend fun update(note : Notes){
        noteDao.update(note.id,note.title,note.note)
    }

}