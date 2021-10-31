package com.example.noteappviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.noteappviewmodel.database.NoteDatabase
import com.example.noteappviewmodel.database.NoteEntity
import com.example.noteappviewmodel.database.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MyViewModel(
    application: Application) : AndroidViewModel(application) {
    private var noteRepository: NoteRepository
    private var notes: LiveData<List<NoteEntity>>
    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        noteRepository = NoteRepository(noteDao)
        notes = noteRepository.getNotes
    }

    fun getNotes(): LiveData<List<NoteEntity>> {
        return notes
    }

    fun addNote(noteText: String){
       CoroutineScope(IO).launch {
           noteRepository.addNote(NoteEntity(0, noteText))
       }
    }
    fun editNote(noteID: Int, noteText: String){
        CoroutineScope(IO).launch {
            noteRepository.updateNote(NoteEntity(noteID,noteText))
        }
    }

    fun deleteNote(noteID: Int){
        CoroutineScope(IO).launch {
            noteRepository.deleteNote(NoteEntity(noteID,""))
        }
    }
}