package com.example.notepad.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.MainActivityContract
import com.example.notepad.R
import com.example.notepad.model.NoteModel
import com.example.notepad.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.layout_note.view.*

class NoteAdapter(val context: Context, val notes: List<NoteModel>, val presenter: MainActivityContract.Presenter):
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_note, parent, false)

        return ViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = notes[position].title
        holder.description.text = notes[position].description

        holder.itemView.delete_note_button.setOnClickListener { presenter.deleteNote(context,
            holder.title.text as String, holder.description.text as String
        ) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title = view.title_text
        var description = view.description_text
    }
}