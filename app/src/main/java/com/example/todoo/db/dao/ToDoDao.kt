package com.example.todoo.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoo.db.model.ToDoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.selects.select

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(todo: ToDoEntity)

    @Delete
    suspend fun delete(todo: ToDoEntity)

    @Query("select * from todoentity where id=:id")
    suspend fun getById(id: Int): ToDoEntity?

    @Query("select *from todoentity")
     fun getToDo(): Flow<List<ToDoEntity>>
}