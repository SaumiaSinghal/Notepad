package com.example.notepad.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notepad.model.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao() : DAOAccess

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabaseClient(context: Context) : NoteDatabase? {
            if (INSTANCE != null) return INSTANCE

            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context.applicationContext, NoteDatabase::class.java, "NOTE_DATABASE")
                    .build()
                return INSTANCE
            }
        }
    }
}