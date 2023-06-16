package com.example.mynotesapp.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "notes_table")
data class Notes(
    @PrimaryKey(autoGenerate = true) var id : Int?,
    @ColumnInfo(name = "Title") var title : String?,
    @ColumnInfo(name = "Note") var note : String?,
    @ColumnInfo(name = "Date") var date : String?
): Serializable
