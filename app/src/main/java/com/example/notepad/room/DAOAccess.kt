package com.example.notepad.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.notepad.model.NoteModel

@Dao
interface DAOAccess {

    @Insert()
    fun insertNote(noteModel: NoteModel)

    @Query("select * from note")
    fun getNotesList(): List<NoteModel>


}