package com.example.todoo.todo_list

import com.example.todoo.db.model.ToDoEntity

sealed class ToDoListEvent {
    data class DeleteToDo(val todo: ToDoEntity) : ToDoListEvent()
    data class OnDoneToDo(val todo: ToDoEntity, val isDone: Boolean) : ToDoListEvent()
    object UndoToDo : ToDoListEvent()
    data class OnToDo(val todo: ToDoEntity) : ToDoListEvent()
    object Add : ToDoListEvent()

}
