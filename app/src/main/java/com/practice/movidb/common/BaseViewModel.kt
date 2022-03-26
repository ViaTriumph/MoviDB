package com.practice.movidb.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModelComm, ViewModel() {

    private val _componentFlow = MutableStateFlow(Component())
    val componentFlow: StateFlow<Component> = _componentFlow

    override fun launchComponent(name: String, data: Any?) {
        viewModelScope.launch {
            _componentFlow.value = Component(name, data)
        }
    }
}

data class Component(
    val name: String = "NONE",
    val data: Any? = null
)


interface ViewModelComm {
    fun launchComponent(name: String, data: Any?)
}