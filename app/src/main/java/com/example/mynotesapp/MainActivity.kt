package com.example.mynotesapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotesapp.Adapters.NotesRecyclerViewAdapter
import com.example.mynotesapp.Database.NoteDatabase
import com.example.mynotesapp.Model.NoteViewModel
import com.example.mynotesapp.Model.Notes
import com.example.mynotesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NotesRecyclerViewAdapter.NotesItemClickListener,
    PopupMenu.OnMenuItemClickListener {


    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    lateinit var viewModel: NoteViewModel
    lateinit var noteAdapter: NotesRecyclerViewAdapter
    lateinit var selectedNotes: Notes


    private val updateNote =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val note = result.data?.getSerializableExtra("note") as? Notes
                if (note != null) {
                    viewModel.updateNote(note)
                }
            }
        }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initModelView()

    }

    private fun initModelView() {

        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)

        viewModel.allnotes.observe(this) { list ->

            list?.let {
                noteAdapter.updateList(list)
            }
        }
        database = NoteDatabase.getDatabase(this)

    }

    fun initUI() {

        binding.apply {

            noteAdapter = NotesRecyclerViewAdapter(this@MainActivity, this@MainActivity)
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
                adapter = noteAdapter
            }


            val getContent =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                    if (result.resultCode == Activity.RESULT_OK) {


                        val note = result.data?.getSerializableExtra("note") as? Notes
                        if (note != null) {
                            viewModel.insertNote(note)
                        }
                    }
                }



            addNoteButton.setOnClickListener {

                getContent.launch(Intent(this@MainActivity, AddNotes::class.java))
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        noteAdapter.filterList(newText)
                    }
                    return true
                }
            })

        }

    }

    override fun onItemClicked(note: Notes) {
        val intent = Intent(this@MainActivity, AddNotes::class.java).apply {
            putExtra("current_note", note)
        }
        updateNote.launch(intent)

    }

    override fun onLongItemClicked(note: Notes, cardView: CardView) {
        selectedNotes = note
        popUpDisplay(cardView)

    }

    private fun popUpDisplay(cardView: CardView) {

        PopupMenu(this, cardView).apply {
            setOnMenuItemClickListener(this@MainActivity)
            inflate(R.menu.pop_up_menu)
        }.also { it.show() }

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_note) {

            viewModel.deleteNote(selectedNotes)
            return true
        }
        return false
    }

}





