package com.example.roomwordsample

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import android.app.Application


class WordViewModel(application: Application) : AndroidViewModel(application) {
    private var mRepository: WordRepository? = null

    private var mAllWords: LiveData<List<Word?>?>? = null

    fun WordViewModel(application: Application?) {
        super(application)
        mRepository = WordRepository(application)
        mAllWords = mRepository!!.getAllWords()
    }

    fun getAllWords(): LiveData<List<Word?>?>? {
        return mAllWords
    }

    fun insert(word: Word?) {
        mRepository!!.insert(word)
    }
}