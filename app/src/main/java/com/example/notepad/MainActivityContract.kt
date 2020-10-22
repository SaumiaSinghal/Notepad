package com.example.notepad

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.notepad.model.NoteModel

interface MainActivityContract {

    interface View {
        fun updateNote(noteModel: NoteModel?)
    }

    interface Presenter {
        val NOTES_FRAGMENT: String
        val BLANK_NOTE_FRAGMENT: String

        fun launchNotesListFragment(fragmentManager: FragmentManager)
        fun launchBlankNoteFragment(fragmentManager: FragmentManager)

        fun getNotesList(context: Context)

        fun saveNote(context: Context, title: String, description: String)

        fun rxUnsubscribe()

        fun setView(view: View)
    }

    interface Model {
    }
}