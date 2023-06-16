package com.example.mynotesapp

import android.app.Activity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mynotesapp.Model.Notes
import com.example.mynotesapp.Utils.showToast
import com.example.mynotesapp.databinding.ActivityAddNotesBinding
import java.util.Date


class AddNotes : AppCompatActivity() {

    private lateinit var binding: ActivityAddNotesBinding
    private lateinit var note: Notes
    private var old_note: Notes? = null
    var isUpdated = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnClick()
        intentData()

    }

    private fun btnClick() {
        binding.apply {
            imgCheck.setOnClickListener {

                val title = etTitle.text.toString()
                val noteDescription = etNotes.text.toString()


                if (title.isNotEmpty() || noteDescription.isNotEmpty()) {

                    val formater = SimpleDateFormat(" d ,MMM,yyyy HH:MM a")

                    if (isUpdated) {
                        note = Notes(old_note?.id, title, noteDescription, formater.format(Date()))
                    } else {
                        note = Notes(null, title, noteDescription, formater.format(Date()))
                    }

                    val intent = Intent().apply {
                        putExtra("note", note)

                    }
                    setResult(Activity.RESULT_OK, intent)
                    finish()


                } else {

                    showToast("Please Enter Some data")
                }


            }

            imgBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

        }
    }


    private fun intentData() {
        binding.apply {

            old_note = intent.getSerializableExtra("current_note") as Notes?
            old_note?.let {
                etTitle.setText(it.title)
                etNotes.setText(it.note)
                isUpdated = true
            }
        }


    }


}



