package com.devshady.captone.stopwatch.domain

sealed class TimerState {
    object Idle: TimerState()
    data class Running(val remainingMills: Long): TimerState()
    data class Paused(val remainingMills: Long): TimerState()
    object Stopped: TimerState()
}