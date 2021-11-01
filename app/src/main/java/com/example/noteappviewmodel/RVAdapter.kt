package com.example.noteappviewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappviewmodel.data.Note
import com.example.noteappviewmodel.database.NoteEntity
import com.example.noteappviewmodel.databinding.NoteRowBinding

class RVAdapter(private val activity: MainActivity): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    private var notes = emptyList<Note>()

    class ItemViewHolder(val binding: NoteRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.ItemViewHolder {
        return ItemViewHolder(
            NoteRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note = notes[position]

        holder.binding.apply {
            tvNote.text = note.noteText
            ibEditNote.setOnClickListener {
                activity.raiseDialog(note.id)
            }
            ibDeleteNote.setOnClickListener {
                activity.checkDeleteDialog(note.id)
            }
        }
    }

    override fun getItemCount() = notes.size

    fun update(notes: List<Note>){
        println("UPDATING DATA")
        this.notes = notes
        notifyDataSetChanged()
    }
}