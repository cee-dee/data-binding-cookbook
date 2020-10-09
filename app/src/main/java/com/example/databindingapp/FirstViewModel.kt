package com.example.databindingapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay
import java.time.LocalDateTime

class FirstViewModel : ViewModel() {

    fun getDate(): LiveData<String> {
        return liveData {
            while (true) {
                emit(LocalDateTime.now().toString())
                delay(1000)
            }
        }
    }
}
