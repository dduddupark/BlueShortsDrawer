package com.bluepark.blueshortsdrawer.ui.shorts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class ShortsViewModel : ViewModel() {

    private var index = 0
    private var shortsList = listOf<Shorts>()

    private val _shorts = MutableSharedFlow<Shorts?>()
    val shorts = _shorts.asSharedFlow()

    fun getList() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {
                //서버 데이터라고 가정하고 데이터 가져오기
                shortsList = list
                if (index == 0) {
                    getShorts()
                }
            }
        }
    }

    fun getShorts() {
        viewModelScope.launch {
            Timber.e("index = $index")
            if (index >= (shortsList.size)) {
                return@launch
            }

            _shorts.emit(shortsList[index])
            index++
        }
    }

    private val list = arrayListOf(
        Shorts("Shorts 1"),
        Shorts("Shorts 2"),
        Shorts("Shorts 3"),
        Shorts("Shorts 4"),
        Shorts("Shorts 5"),
        Shorts("Shorts 6"),
        Shorts("Shorts 7"),
        Shorts("Shorts 8"),
        Shorts("Shorts 9"),
        Shorts("Shorts 10"),
        Shorts("Shorts 11"),
        Shorts("Shorts 12"),
        Shorts("Shorts 13"),
        Shorts("Shorts 14"),
        Shorts("Shorts 15")
    )

}