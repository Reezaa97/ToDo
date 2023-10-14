package com.example.todoo.util

sealed class UiEvent {
    object PopBack : UiEvent()
     data class Navigate(val rote: String) : UiEvent()
   data class ShowSnackBar(val message: String, val action: String? = null) : UiEvent()

}

