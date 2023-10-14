package com.example.todoo.add_edit_todo

sealed class AddEditTodoEvent {
    data class onTitleChange(val title: String) : AddEditTodoEvent()
    data class onDescriptionChange(val selectTimeText: String) : AddEditTodoEvent()
    object onSaveTodoClick:AddEditTodoEvent()

}


