package com.example.mynotesapp.Model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mynotesapp.Database.NoteDatabase
import com.example.mynotesapp.Database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class NoteViewModel(application: Application): AndroidViewModel(application) {

    private val repository :NoteRepository

    val allnotes : LiveData<List<Notes>>
    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NoteRepository(dao)
        allnotes = repository.allNotes
    }

    fun deleteNote(note : Notes) = viewModelScope.launch ( Dispatchers.IO ){
        repository.delete(note)
    }

    fun insertNote(note: Notes) = viewModelScope.launch ( Dispatchers.IO ){
        repository.insert(note)
    }

    fun updateNote(note : Notes) = viewModelScope.launch ( Dispatchers.IO ){
        repository.update(note)
    }


}