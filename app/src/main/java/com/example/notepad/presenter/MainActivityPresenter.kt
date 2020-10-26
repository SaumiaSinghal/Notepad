package com.example.notepad.presenter

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.notepad.MainActivityContract
import com.example.notepad.R
import com.example.notepad.model.MainActivityModel
import com.example.notepad.view.BlankNoteFragment
import com.example.notepad.view.NotesFragment
import io.reactivex.rxjava3.disposables.Disposable

class MainActivityPresenter: MainActivityContract.Presenter {

    override val NOTES_FRAGMENT = "NOTES_LIST_FRAGMENT"
    override val BLANK_NOTE_FRAGMENT = "BLANK_NOTE_FRAGMENT"

    private var view: MainActivityContract.View? = null
    private var model: MainActivityContract.Model = MainActivityModel()

    private var disposable: Disposable? = null

    override fun launchNotesListFragment(fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentHolder, NotesFragment(), NOTES_FRAGMENT)
            .addToBackStack(null)
            .commit()
    }

    override fun launchBlankNoteFragment(fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentHolder, BlankNoteFragment(), BLANK_NOTE_FRAGMENT)
            .addToBackStack(null)
            .commit()
    }

    override fun getNotesList(context: Context) {
        model.fetchNotesListFromDatabase(context)?.subscribe(
                {
                    it?.let { it1 -> view?.updateNoteAdapter(it1) }
                    it?.forEach { note -> Log.d("Testing:", note.title + ": "+ note.description) }
                },
                {

                }
            )
    }

    override fun saveNote(context: Context, title: String, description: String) {
        model.saveNoteToDatabase(context, title, description).subscribe(
                {
                    view?.updateNoteList(it)
                    Log.d("Testing: saved note", it.title + ": "+ it.description)
                },
                {

                }
            )
    }

    override fun deleteNote(context: Context, title: String?, description: String?) {
        model.deleteNoteInDatabase(context, title, description)?.subscribe(
                {
                    it?.let { it1 -> view?.updateNoteAdapter(it1) }
                },
                {

                }
            )
    }

    override fun rxUnsubscribe() {
        disposable?.dispose()
    }

    override fun setView(view: MainActivityContract.View) {
        this.view = view
    }
}