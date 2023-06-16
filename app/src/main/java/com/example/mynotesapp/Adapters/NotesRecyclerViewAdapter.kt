package com.example.mynotesapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.Model.Notes
import com.example.mynotesapp.R
import kotlin.random.Random

class NotesRecyclerViewAdapter(private val context : Context , val listener: NotesItemClickListener)
    : RecyclerView.Adapter<NotesRecyclerViewAdapter.NoteViewHolder>() {


    private val NotesList = ArrayList<Notes>()
    private val fullList = ArrayList<Notes>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return  NoteViewHolder(

            LayoutInflater.from(context).inflate(R.layout.list_item, parent,false)

        )
    }

    override fun getItemCount(): Int {
       return NotesList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote =  NotesList[position]
        holder.title.text = currentNote.title
        holder.title.isSelected = true

        holder.note.text = currentNote.note
        holder.date.text = currentNote.date
        holder.date.isSelected = true

        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(randomColorGenerater(),null))

        holder.notes_layout.setOnClickListener{

            listener.onItemClicked(NotesList[holder.adapterPosition])
        }

        holder.notes_layout.setOnClickListener{

            listener.onItemClicked(NotesList[holder.adapterPosition])
        }

        holder.notes_layout.setOnLongClickListener{
            listener.onLongItemClicked(NotesList[holder.adapterPosition],holder.notes_layout)
            true
        }

    }


    fun randomColorGenerater() : Int{

        val colorList = ArrayList<Int>()
        colorList.add(R.color.note_color1)
        colorList.add(R.color.note_color2)
        colorList.add(R.color.note_color3)
        colorList.add(R.color.note_color4)
        colorList.add(R.color.note_color5)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(colorList.size)
        return colorList[randomIndex]
    }


    inner class NoteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val notes_layout  = itemView.findViewById<CardView>(R.id.card_layout)
        val title  = itemView.findViewById<TextView>(R.id.text_title)
        val note  = itemView.findViewById<TextView>(R.id.text_note)
        val date  = itemView.findViewById<TextView>(R.id.text_date)


    }

    fun updateList(newList : List<Notes>){

        fullList.clear()
        fullList.addAll(newList)

        NotesList.clear()
        NotesList.addAll(fullList)
        notifyDataSetChanged()

    }

    fun filterList(search : String){
        NotesList.clear()

        for (item in fullList){
            if(item.title?.lowercase()?.contains(search.lowercase())==true||
                item.note?.lowercase()?.contains(search.lowercase())==true)

                NotesList.add(item)
        }

        notifyDataSetChanged()
    }






    interface NotesItemClickListener{

        fun onItemClicked(note:Notes)
        fun onLongItemClicked(note:Notes,cardView: CardView)

    }


}