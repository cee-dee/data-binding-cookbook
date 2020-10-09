package com.example.databindingapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.delay
import java.time.LocalDateTime

class FirstViewModel : ViewModel() {

    private var count = 0

    val dateEmitCount = liveData {
        while (true) {
            count++
            emit(count)
            delay(1000 + (5000 * Math.random()).toLong())
        }
    }

    val date = dateEmitCount.map {
        LocalDateTime.now().toString()
    }

    fun reset() {
        count = 0
    }

}
