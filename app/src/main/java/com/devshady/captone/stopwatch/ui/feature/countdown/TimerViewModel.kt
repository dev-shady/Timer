package com.devshady.captone.stopwatch.ui.feature.countdown

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devshady.captone.stopwatch.data.CoroutineTimerController
import com.devshady.captone.stopwatch.domain.TimerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class TimerViewModel(applicationScope: CoroutineScope) : ViewModel() {

    data class TimerUiState(
        val timerState: TimerState
    ) {
        private val totalSeconds: Long
            get() = when (timerState) {

                is TimerState.Paused -> timerState.remainingMills / 1000
                is TimerState.Running -> timerState.remainingMills / 1000
                else -> 0L
            }
        val hours: Long = totalSeconds / 3600
        val minutes: Long = (totalSeconds % 3600) / 60
        val seconds: Long = totalSeconds % 60

        val formattedTime: String
            get() = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    val timerController =
        CoroutineTimerController.getInstance(applicationScope)

    private val uiStateMutable =
        MutableStateFlow<TimerUiState>(TimerUiState(TimerState.Idle))
    val uiState = uiStateMutable.asStateFlow()

    fun start(totalTimeInSeconds: Long) {
        timerController.timerState
            .onEach { newTimerState ->
                uiStateMutable.update {
                    it.copy(
                        timerState = newTimerState,
                    )
                }
            }
            .launchIn(viewModelScope)
        timerController.start(totalTimeInSeconds * 1000, UPDATE_INTERVAL)
    }

    companion object {
        private const val UPDATE_INTERVAL = 1000L
        fun providerFactory(applicationScope: CoroutineScope): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TimerViewModel(applicationScope) as T
                }
            }
        }
    }
}