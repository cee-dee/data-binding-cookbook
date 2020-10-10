package com.example.databindingapp

import androidx.lifecycle.ViewModel
import java.time.LocalDateTime

class FirstViewModel : ViewModel() {

    fun getDate(): String {
        return LocalDateTime.now().toString()
    }
}
