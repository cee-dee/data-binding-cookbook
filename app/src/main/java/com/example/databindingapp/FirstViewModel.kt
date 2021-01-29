package com.example.databindingapp

import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
class FirstViewModel : ViewModel() {

    private var count = 0
    private var countAfterEnabling = 0

    val dateEmitCount = liveData {
        while (true) {
            enable.awaitTrue()

            count++
            countAfterEnabling++
            emit(count)

            if (countAfterEnabling >= 3) {
                enable.value = false
                countAfterEnabling = 0
            }

            delay(1000 + (5000 * Math.random()).toLong())
        }
    }

    val date = dateEmitCount.map {
        LocalDateTime.now().toString()
    }

    fun reset() {
        count = 0
    }

    private val _snackbarEvent = MutableLiveData<Event<SnackbarCommand>>()
    val snackbarEvent: LiveData<Event<SnackbarCommand>> = _snackbarEvent

    fun showSnackbar(text: String, anchor: View) {
        _snackbarEvent.value = Event(SnackbarCommand(text, anchor))
    }

    val enable: MutableLiveData<Boolean> = MutableLiveData(true)

}
