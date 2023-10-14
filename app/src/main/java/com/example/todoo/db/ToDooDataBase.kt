package com.example.todoo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoo.db.dao.ToDoDao
import com.example.todoo.db.model.ToDoEntity

@Database(entities = [ToDoEntity::class], version = 6)
 abstract class ToDooDataBase: RoomDatabase() {
     abstract val dao:ToDoDao
}