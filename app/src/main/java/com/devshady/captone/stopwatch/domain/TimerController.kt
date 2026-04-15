package com.devshady.captone.stopwatch.domain

import kotlinx.coroutines.flow.Flow

interface TimerController {
    val timerState: Flow<TimerState>
    fun start(totalTimeInMillis: Long, updateInterval: Long)
    fun pause()
    fun stop()
    fun reset()
}