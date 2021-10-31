package com.example.noteappviewmodel

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var btSubmit : Button
    private lateinit var tvNewNote : EditText
    private lateinit var rvNotes : RecyclerView
    private lateinit var rvAdapter:RVAdapter

    lateinit var mainViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        mainViewModel.getNotes().observe(
            this,
            { notes ->
                rvAdapter.update(notes)
            }
        )

        rvNotes = findViewById(R.id.rvNotes)
        btSubmit = findViewById(R.id.btSubmit)
        tvNewNote = findViewById(R.id.tvNewNote)


        btSubmit.setOnClickListener {
            if(tvNewNote.text.isNotBlank() ){
                val newNote = tvNewNote.text.toString()
                mainViewModel.addNote(newNote)
                tvNewNote.text.clear()
                tvNewNote.clearFocus()
                Toast.makeText(this, "Note Added", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "please add a message", Toast.LENGTH_LONG).show()
            }
        }
        rvAdapter = RVAdapter(this)
        rvNotes.adapter = rvAdapter
        rvNotes.layoutManager = LinearLayoutManager(this)

    }


    fun raiseDialog(id: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Enter new text"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {

                    _, _ ->
                run{
                    mainViewModel.editNote(id, updatedNote.text.toString())
                    Toast.makeText(this, "Note Updated", Toast.LENGTH_LONG).show()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }
    fun checkDeleteDialog(id: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        val checkTextView = TextView(this)
        checkTextView.text = "  Are sure you want to delete this note ?!"

        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {

                    _, _ ->
                run{
                    mainViewModel.deleteNote(id)
                    Toast.makeText(this, "Note deleted", Toast.LENGTH_LONG).show()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Check the deletion")
        alert.setView(checkTextView)
        alert.show()
    }
}
