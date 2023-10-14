package com.example.todoo.screen

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoo.R
import com.example.todoo.add_edit_todo.AddEditTodoEvent
import com.example.todoo.util.UiEvent
import com.example.todoo.viewModel.AddEditToDoViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditToDoViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var hour = calendar[Calendar.HOUR_OF_DAY]
    var minute = calendar[Calendar.MINUTE]
    var timePicker = TimePickerDialog(context, { _, selectedHour: Int,
                                                 selectedMinute: Int ->
        viewModel.selectTimeText = "$selectedHour:$selectedMinute"
    }, hour, minute, false)

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBack -> onPopBackStack()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }

                else -> Unit

            }

        }
    }
    Scaffold(scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditTodoEvent.onSaveTodoClick)
            },

                contentColor = MaterialTheme.colorScheme.primary,
            )
             {
                Icon(imageVector = Icons.Default.Check, contentDescription = "save")

            }
        }
    ) {


        Column(Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = viewModel.title,
                onValueChange = { viewModel.onEvent(AddEditTodoEvent.onTitleChange(it)) },
                label = { Text(text = "Title")}, modifier = Modifier.fillMaxWidth(),
                singleLine = false, maxLines = 5
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row() {


                TextButton(
                    onClick = { timePicker.show() },
                    modifier = Modifier.weight(1f)
                ) {
                    Row {

                        Icon(
                            painter = painterResource(R.drawable.baseline_alarm_24),
                            contentDescription =""
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "Remind me")
                        Spacer(modifier = Modifier.width(10.dp))

                        Text(text = viewModel.selectTimeText)

                    }
                }
            }
        }

    }

}