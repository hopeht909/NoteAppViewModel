package com.example.noteappviewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noteappviewmodel.data.Note
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val notes: MutableLiveData<List<Note>> = MutableLiveData()
    private var db: FirebaseFirestore = Firebase.firestore


    fun getNotes(): LiveData<List<Note>> {
        return notes
    }

    fun getData() {
        db.collection("notes")
            //get data from firebase
            .get()
            //Listener called when a task completes successfully
            .addOnSuccessListener {
            //result is an object from querySnapshot allow us to have easier access to the db
                    result ->
            // a temporary list to store all the data in the firebase like "data" "id"
                val tempNote = arrayListOf<Note>()

                for (documents in result) {
                    documents.data.map { (key, value) ->
                        //
                        tempNote.add(Note(documents.id, value.toString()))
                    }
                }
            // add all the data in tempNote to our global list called notes
                notes.postValue(tempNote)
            }
            //Listener called when a task fails with an exception
            .addOnFailureListener {
                    exception -> Log.w("MainActivity", "Error getting documents.", exception)
            }
    }

    fun addNote(note : Note) {
        CoroutineScope(IO).launch {
            val newNote = hashMapOf(
                "noteText" to note.noteText
            )
            db.collection("notes")
                .add(newNote)
            getData()
        }
    }

    fun editNote(noteID: String, noteText: String) {
        CoroutineScope(IO).launch {
            db.collection("notes")
                .get()
                .addOnSuccessListener { result ->
                    for (documents in result) {
                        if(documents.id == noteID){
                            db.collection("notes").document(noteID).update("noteText", noteText)
                        }
                    }
                    getData()
                }
                .addOnFailureListener { exception ->
                    Log.w("MainActivity", "Error getting documents.", exception)
                }
        }
    }

    fun deleteNote(noteID: String) {
        CoroutineScope(IO).launch {
            db.collection("notes")
                .get()
                .addOnSuccessListener { result ->
                    for (documents in result) {
                        if(documents.id == noteID){
                            db.collection("notes").document(noteID).delete()
                        }
                    }
                    getData()
                }
                .addOnFailureListener { exception ->
                    Log.w("MainActivity", "Error getting documents.", exception)
                }
        }
    }
}