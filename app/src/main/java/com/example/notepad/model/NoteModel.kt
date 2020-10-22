package com.example.notepad.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "note")
data class NoteModel(

    @PrimaryKey(autoGenerate = true)
    var uid: Long,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String,
)
//{
//    constructor(title: String, description: String):this (null, title, description)
//}