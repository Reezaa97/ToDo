package com.example.todoo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.resource.bitmap.Rotate
import com.example.todoo.db.model.ToDoEntity
import com.example.todoo.repository.ToDooRepository
import com.example.todoo.todo_list.ToDoListEvent
import com.example.todoo.util.Routes
import com.example.todoo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor
    (private val repository: ToDooRepository) : ViewModel() {
    val todo = repository.getToDo()
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private var deletedTodo: ToDoEntity? = null
    fun onEvent(event: ToDoListEvent) {
        when (event) {
            is ToDoListEvent.DeleteToDo -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    repository.delete(event.todo)
                    sendUiEvent(UiEvent.ShowSnackBar("ToDo deleted", "Undo"))
                }
            }

            is ToDoListEvent.OnDoneToDo -> {
                viewModelScope.launch {
                    repository.add(
                        event.todo.copy(isDone = event.isDone)
                    )
                }
            }

            is ToDoListEvent.UndoToDo -> {
                deletedTodo?.let { todo -> viewModelScope.launch { repository.add(todo) } }
            }

            is ToDoListEvent.Add -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }

            is ToDoListEvent.OnToDo -> {
                sendUiEvent(
                    UiEvent.Navigate
                        (Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}")
                )
            }

        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}