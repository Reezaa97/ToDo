package com.example.todoo.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ToDoEntity(
    var title: String, var selectTimeText:String="",
    var isDone: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
)
