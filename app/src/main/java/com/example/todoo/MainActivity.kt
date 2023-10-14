package com.example.todoo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Colors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoo.db.model.ToDoEntity
import com.example.todoo.screen.AddEditScreen
import com.example.todoo.screen.TodoListScreen
import com.example.todoo.ui.theme.AppDark
import com.example.todoo.ui.theme.ToDooTheme
import com.example.todoo.ui.theme.pp
import com.example.todoo.util.Routes
import com.example.todoo.viewModel.ToDoListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDooTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView()


                }
            }
        }
    }
}
                @Composable
                fun MainView() {



                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = Routes.TODO_LIST
                        ) {
                            composable(Routes.TODO_LIST) {
                                TodoListScreen(
                                    onNavigate = {
                                        navController.navigate(it.rote)
                                    }
                                )
                            }
                            composable(
                                route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
                                arguments = listOf(navArgument(name = "todoId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                })
                            ) {
                                AddEditScreen(onPopBackStack = { navController.popBackStack() })
                            }
                        }
                    }












