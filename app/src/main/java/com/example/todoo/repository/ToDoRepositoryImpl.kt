package com.example.todoo.repository

import com.example.todoo.db.dao.ToDoDao
import com.example.todoo.db.model.ToDoEntity
import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl(private val dao: ToDoDao) : ToDooRepository {
    override suspend fun add(todo: ToDoEntity) {
        dao.add(todo)
    }

    override suspend fun delete(todo: ToDoEntity) {
        dao.delete(todo)
    }

    override suspend fun getById(id: Int): ToDoEntity? {
        return dao.getById(id)
    }

    override  fun getToDo(): Flow<List<ToDoEntity>> {
        return dao.getToDo()
    }
}