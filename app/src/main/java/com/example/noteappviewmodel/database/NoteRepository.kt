package com.example.noteappviewmodel.database

import androidx.lifecycle.LiveData

//class that make it easier to reach the methods we created in Doa CLass
class NoteRepository(private val noteDao: NoteDao) {

    // LiveData
    val getNotes: LiveData<List<NoteEntity>> = noteDao.getNotes()

    // suspend fun so we can reach it in the Main easily and resume it whenever
    suspend fun addNote(note: NoteEntity){
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: NoteEntity){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: NoteEntity){
        noteDao.deleteNote(note)
    }

}