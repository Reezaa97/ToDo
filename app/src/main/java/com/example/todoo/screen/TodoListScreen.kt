package com.example.todoo.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoo.todo_list.ToDoListEvent
import com.example.todoo.ui.theme.AppDark
import com.example.todoo.util.UiEvent
import com.example.todoo.viewModel.ToDoListViewModel

@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState",
    "UnusedMaterial3ScaffoldPaddingParameter"
)
@Composable
fun TodoListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ToDoListViewModel = hiltViewModel()
) {

    val todoo = viewModel.todo.collectAsState(initial = emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {


                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result ==androidx.compose.material3.SnackbarResult.ActionPerformed) {

                            viewModel.onEvent(ToDoListEvent.UndoToDo)

                    }

                }

                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }

        }

    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(ToDoListEvent.Add)
                },
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")

            }
        }
    ) {



            Column {


                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = "Just Do It",
                    fontSize = 40.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center

                )

                Spacer(modifier = Modifier.height(30.dp))



                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                )

                {


                    items(todoo.value) { todoo ->
                        TodoItem(todo = todoo, onEvent = viewModel::onEvent,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onEvent(ToDoListEvent.OnToDo(todoo))
                                }
                                .padding(16.dp)
                        )
                    }

                }
            }


    }
}



