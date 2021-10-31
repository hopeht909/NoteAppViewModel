package com.example.noteappviewmodel.database

import androidx.room.Entity
import androidx.room.PrimaryKey

//table name
@Entity(tableName = "NotesTable")
data class NoteEntity(

    // table contents
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val noteText: String)