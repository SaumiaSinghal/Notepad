package com.example.notepad.model

import android.content.Context
import com.example.notepad.MainActivityContract
import com.example.notepad.room.DAOAccess
import com.example.notepad.room.NoteDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class MainActivityModel : MainActivityContract.Model {

    override fun fetchNotesListFromDatabase(context: Context): Observable<List<NoteModel>?>? {
        return Observable.fromCallable {
            getDaoAccess(context)?.getNotesList()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveNoteToDatabase(context: Context, title: String, description: String): Observable<Unit?>? {
        return Observable.fromCallable {
            val noteModel = NoteModel(UUID.randomUUID().mostSignificantBits, title, description)
            with(getDaoAccess(context)) {
                this?.insertNote(noteModel)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteNoteInDatabase(context: Context, title: String?, description: String?): Observable<Unit?>? {
        return Observable.fromCallable {
            getDaoAccess(context)?.deleteNote(title, description)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    private fun getDaoAccess(context: Context): DAOAccess? {
        return NoteDatabase.getDatabaseClient(context)?.noteDao()
    }
}