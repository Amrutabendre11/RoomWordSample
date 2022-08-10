package com.example.roomwordsample

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText

class NewWordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        val mEditWordView = findViewById<EditText>(R.id.edit_word)
        val button= findViewById<Button>(R.id.butt1on_save1)
        button.setOnClickListener(object : DialogInterface.OnClickListener() {
            fun onClick(view: View?) {
                val replyIntent = Intent()
                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    setResult(AppCompatActivity.RESULT_CANCELED, replyIntent)
                } else {
                    val word = mEditWordView.getText().toString()
                    replyIntent.putExtra(NewWordActivity.EXTRA_REPLY, word)
                    setResult(AppCompatActivity.RESULT_OK, replyIntent)
                }
                finish()
            }
        })
    }
    companion object {
        const val EXTRA_REPLY = "com.example.android.roomwordssample.REPLY"
    }
}