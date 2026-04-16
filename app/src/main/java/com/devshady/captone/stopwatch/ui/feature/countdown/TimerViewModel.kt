package com.devshady.captone.stopwatch.ui.feature.countdown

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devshady.captone.stopwatch.data.CoroutineTimerController
import com.devshady.captone.stopwatch.domain.TimerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {

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

    companion object {
        private const val UPDATE_INTERVAL = 1000L
    }

    val timerController =
        CoroutineTimerController.getInstance(viewModelScope) //TODO inject application scope

    private val uiStateMutable =
        MutableStateFlow<TimerUiState>(TimerUiState(TimerState.Idle))
    val uiState = uiStateMutable.asStateFlow()

    fun start(totalTimeInSeconds: Long) {
        viewModelScope.launch {
            timerController.timerState.collect { newTimerState ->
                uiStateMutable.update { currentState ->
                    currentState.copy(
                        timerState = newTimerState,
                    )
                }
            }
        }
        timerController.start(totalTimeInSeconds * 1000, UPDATE_INTERVAL)
    }
}