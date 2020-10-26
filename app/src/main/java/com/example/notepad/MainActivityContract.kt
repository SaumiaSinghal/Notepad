package com.example.notepad

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.notepad.model.NoteModel
import io.reactivex.rxjava3.core.Observable

interface MainActivityContract {

    interface View {
        fun updateNoteAdapter(notes: List<NoteModel>)
    }

    interface Presenter {
        val NOTES_FRAGMENT: String
        val BLANK_NOTE_FRAGMENT: String

        fun launchNotesListFragment(fragmentManager: FragmentManager)
        fun launchBlankNoteFragment(fragmentManager: FragmentManager)

        fun getNotesList(context: Context)
        fun saveNote(context: Context, title: String, description: String)
        fun deleteNote(context: Context, title: String?, description: String?)

        fun rxUnsubscribe()
        fun setView(view: View)
    }

    interface Model {
        fun fetchNotesListFromDatabase(context: Context): Observable<List<NoteModel>?>?
        fun saveNoteToDatabase(context: Context, title: String, description: String) : Observable<Unit?>?
        fun deleteNoteInDatabase(context: Context, title: String?, description: String?): Observable<Unit?>?
    }
}