package com.example.roomwordsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager

import android.R
import android.app.Activity
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProviders
import android.widget.Toast

import android.content.Intent


const val NEW_WORD_ACTIVITY_REQUEST_CODE = 1
class MainActivity : AppCompatActivity() {
    private var mWordViewModel: WordViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mWordViewModel!!.getAllWords()!!.observe(this, object : Observer<List<Word?>?>() {
            override fun onChanged(@Nullable words: List<Word?>?) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words as List<Word>?)
            }
        })
    }
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            val word = Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY)!!)
            mWordViewModel!!.insert(word)
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
/*
14
In MainActivity,start NewWordActivity when the user taps the FAB. Replace the code in the FAB's onClick() click handler with the following code:

Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
Run your app. When you add a word to the database in NewWordActivity, the UI automatically updates.
Add a word that already exists in the list. What happens? Does your app crash? Your app uses the word itself as the primary key, and each primary key must be unique. You can specify a conflict strategy to tell your app what to do when the user tries to add an existing word.
In the WordDao interface, change the annotation for the insert() method to:

@Insert(onConflict = OnConflictStrategy.IGNORE)
 */