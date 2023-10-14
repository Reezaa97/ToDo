package com.example.todoo.repository


import com.example.todoo.db.model.ToDoEntity
import kotlinx.coroutines.flow.Flow


interface ToDooRepository {
    suspend fun add(todo: ToDoEntity)

    suspend fun delete(todo: ToDoEntity)

    suspend fun getById(id: Int): ToDoEntity?

       fun getToDo(): Flow<List<ToDoEntity>>

}