package com.example.databindingapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged

fun <T> MutableLiveData<T>.setDistinctNonNullValue(newValue: T?) {
    if ((newValue != null) && (value != newValue)) {
        value = newValue
    }
}

val noChange = null

class TwoWayLiveData<S, T>(
        initialValue: S,
        valueChangeCallback: (S, T?) -> S?,
        dependencyLiveData: LiveData<T>,
        dependencyChangedCallback: (S?, T) -> S?
) : MediatorLiveData<S>() {
    init {
        value = initialValue
        val valueChanged = { newValue: S ->
            val modifiedValue = valueChangeCallback(newValue, dependencyLiveData.value)
            setDistinctNonNullValue(modifiedValue)
        }
        addSource(distinctUntilChanged(), valueChanged)
        val dependencyChanged = { newValue: T ->
            val modifiedValue = dependencyChangedCallback(value, newValue)
            setDistinctNonNullValue(modifiedValue)

        }
        addSource(dependencyLiveData.distinctUntilChanged(), dependencyChanged)
    }
}

class SimpleTwoWayLiveData<S>(
        initialValue: S,
        valueChangeCallback: (S) -> S?
) : MediatorLiveData<S>() {
    init {
        value = initialValue
        val valueChanged = { newValue: S ->
            val modifiedValue = valueChangeCallback(newValue)
            setDistinctNonNullValue(modifiedValue)
        }
        addSource(distinctUntilChanged(), valueChanged)
    }
}
