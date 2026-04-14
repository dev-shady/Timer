package com.devshady.captone.stopwatch.data

import android.os.SystemClock
import com.devshady.captone.stopwatch.domain.TimerController
import com.devshady.captone.stopwatch.domain.TimerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CoroutineTimerController: TimerController {
    private val timeFlowMutable = MutableStateFlow<TimerState>(TimerState.Idle)
    override val timeFlow = timeFlowMutable.asStateFlow()

    override fun start(endTime: Long, updateInterval: Long) {

    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun reset() {
        TODO("Not yet implemented")
    }
}