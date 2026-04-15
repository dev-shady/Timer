package com.devshady.captone.stopwatch.data

import android.os.SystemClock
import com.devshady.captone.stopwatch.domain.TimerController
import com.devshady.captone.stopwatch.domain.TimerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

internal class CoroutineTimerController private constructor(val scope: CoroutineScope) :
    TimerController {

    private val timeFlowMutable = MutableStateFlow<TimerState>(TimerState.Idle)
    override val timerState = timeFlowMutable.asStateFlow()
    private var timerJob: Job? = null

    companion object {
        private var instance: TimerController? = null
        fun getInstance(scope: CoroutineScope): TimerController {
            return instance ?: synchronized(this) {
                instance ?: CoroutineTimerController(scope).also { instance = it }
            }
        }
    }

    override fun start(totalTimeInMillis: Long, updateInterval: Long) {
        val endClockTime = SystemClock.elapsedRealtime() + totalTimeInMillis
        timerJob?.cancel()
        timerJob = scope.launch(Dispatchers.Default) {
            // start the countdown timer off main thread
            while (isActive && SystemClock.elapsedRealtime() < endClockTime) {
                timeFlowMutable.value =
                    TimerState.Running(endClockTime - SystemClock.elapsedRealtime())
                delay(updateInterval)
            }

            if (isActive) {
                timeFlowMutable.value = TimerState.Finished
            }
        }
    }

    override fun pause() {
        timerJob?.cancel()
    }

    override fun stop() {
        timerJob?.cancel()
    }

    override fun reset() {
        TODO("Not yet implemented")
    }
}