package com.example.databindingapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
suspend fun LiveData<Boolean>.awaitTrue() {
    return withContext(Dispatchers.Main.immediate) {
        suspendCancellableCoroutine { continuation ->
            val observer = object : Observer<Boolean> {
                override fun onChanged(value: Boolean) {
                    if (value) {
                        removeObserver(this)
                        continuation.resume(Unit) { }
                    }
                }
            }

            observeForever(observer)

            continuation.invokeOnCancellation {
                removeObserver(observer)
            }
        }
    }
}
