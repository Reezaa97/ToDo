package com.example.todoo.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoo.db.model.ToDoEntity
import com.example.todoo.todo_list.ToDoListEvent

@Composable
fun TodoItem(todo: ToDoEntity, modifier: Modifier, onEvent: (ToDoListEvent) -> Unit) {

    ElevatedCard(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(), shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)

    ) {
        Row {

            Column(Modifier.padding(5.dp)) {


                if (todo.isDone) {
                    Text(
                        text = todo.title,
                        textDecoration = TextDecoration.LineThrough,
                        fontSize = 20.sp
                    )


                } else {
                    Text(text = todo.title, fontSize = 20.sp)
                }



                Spacer(modifier = Modifier.height(5.dp))


            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                onEvent(ToDoListEvent.DeleteToDo(todo))

            }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete"
                )

            }
            Spacer(modifier = Modifier.width(8.dp))
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = { isDone ->
                    onEvent(ToDoListEvent.OnDoneToDo(todo, isDone))

                },
            )



        }


    }

}





