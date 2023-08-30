package com.bluepark.blueshortsdrawer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _closeDrawer = MutableSharedFlow<Boolean>()
    val closeDrawer = _closeDrawer.asSharedFlow()

    fun closeDrawer() {
        viewModelScope.launch {
            _closeDrawer.emit(true)
        }
    }
}