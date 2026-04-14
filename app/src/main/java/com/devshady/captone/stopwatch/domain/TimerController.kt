package com.devshady.captone.stopwatch.domain

import kotlinx.coroutines.flow.Flow

interface TimerController {
    val timeFlow: Flow<TimerState>
    fun start(endTime: Long, updateInterval: Long)
    fun pause()
    fun stop()
    fun reset()
}