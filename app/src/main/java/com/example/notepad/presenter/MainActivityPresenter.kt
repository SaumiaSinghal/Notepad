package com.example.notepad.presenter

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.notepad.MainActivityContract
import com.example.notepad.R
import com.example.notepad.model.NoteModel
import com.example.notepad.room.DAOAccess
import com.example.notepad.room.NoteDatabase
import com.example.notepad.view.BlankNoteFragment
import com.example.notepad.view.NotesFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivityPresenter: MainActivityContract.Presenter {

    override val NOTES_FRAGMENT = "NOTES_LIST_FRAGMENT"
    override val BLANK_NOTE_FRAGMENT = "BLANK_NOTE_FRAGMENT"

    private var view: MainActivityContract.View? = null

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
        Observable.fromCallable {
            getDaoAccess(context)?.getNotesList()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    it?.forEach { note -> Log.d("testing", note.title + ": "+ note.description) }
                },
                {

                }
            )
    }

    override fun saveNote(context: Context, title: String, description: String) {
        Observable.fromCallable {
            val noteModel = NoteModel(1, title, description)
            with(getDaoAccess(context)) {
                this?.insertNote(noteModel)
            }
            noteModel
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("testing", it.title + ": "+ it.description)
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

    private fun getDaoAccess(context: Context): DAOAccess? {
        return NoteDatabase.getDatabaseClient(context)?.noteDao()
    }
}