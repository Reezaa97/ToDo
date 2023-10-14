package com.example.todoo.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoo.add_edit_todo.AddEditTodoEvent
import com.example.todoo.db.model.ToDoEntity
import com.example.todoo.repository.ToDooRepository
import com.example.todoo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditToDoViewModel @Inject constructor(
    private val repository: ToDooRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var todo by mutableStateOf<ToDoEntity?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var selectTimeText by mutableStateOf("")

    private val _uiEvent = Channel<UiEvent> { }
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")
        if (todoId != -1) {
            viewModelScope.launch {
                repository.getById(todoId!!)?.let { todo ->
                    title = todo.title
                    selectTimeText = todo.selectTimeText ?: ""
                    this@AddEditToDoViewModel.todo = todo
                }
            }
        }

    }

    fun onEvent(event: AddEditTodoEvent) {
        when (event) {
            is AddEditTodoEvent.onTitleChange -> {
                title = event.title
            }

            is AddEditTodoEvent.onDescriptionChange -> {
                selectTimeText = event.selectTimeText
            }

            is AddEditTodoEvent.onSaveTodoClick -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendUiEvent(
                            UiEvent.ShowSnackBar(
                                message = "The title can't be empty"
                            )
                        )
                        return@launch

                    }
                    repository.add(
                        ToDoEntity(
                            title = title,
                            selectTimeText = selectTimeText,
                            isDone = todo?.isDone ?: false,
                            id = todo?.id
                        )
                    )
                    sendUiEvent(UiEvent.PopBack)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}