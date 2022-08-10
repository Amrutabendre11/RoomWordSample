package com.example.roomwordsample

import android.R
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.SupportSQLiteDatabase

import androidx.annotation.NonNull

import androidx.room.RoomDatabase
import android.os.AsyncTask
import android.text.TextUtils
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase :RoomDatabase() {
    abstract fun wordDao(): WordDao?
    private var INSTANCE: WordRoomDatabase? = null

    fun getDatabase(context: Context): WordRoomDatabase? {
        if (INSTANCE == null) {
            synchronized(WordRoomDatabase::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        WordRoomDatabase::class.java, "word_database"
                    ) // Wipes and rebuilds instead of migrating
                        // if no Migration object.
                        // Migration is not part of this practical.
                        .fallbackToDestructiveMigration()
                        .addCallback(sRoomDatabaseCallback)
                        .build()

                }
            }
        }
        return INSTANCE
    }

    private val sRoomDatabaseCallback: Callback = object : Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { PopulateDbAsync(it).execute() }
        }
    }

    private class PopulateDbAsync internal constructor(db: WordRoomDatabase) :
        AsyncTask<Void?, Void?, Void?>() {
        private val mDao: WordDao?
        var words = arrayOf("dolphin", "crocodile", "cobra")

        init {
            mDao = db.wordDao()
        }

        override fun doInBackground(vararg params: Void?): Void? {
            mDao!!.deleteAll()
            for (i in 0..words.size - 1) {
                val word = Word(words[i])
                mDao.insert(word)
            }
            return null
        }
    }
}
