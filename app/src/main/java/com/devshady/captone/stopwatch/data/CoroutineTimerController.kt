package com.devshady.captone.stopwatch.data

import android.os.SystemClock
import com.devshady.captone.stopwatch.domain.TimerController
import com.devshady.captone.stopwatch.domain.TimerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn

internal class CoroutineTimerController private constructor(applicationScope: CoroutineScope) :
    TimerController {

    private val timerCommand = MutableStateFlow<TimerCommand>(TimerCommand.Stop)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val timerState: StateFlow<TimerState> = timerCommand.flatMapLatest { command ->
        when (command) {
            is TimerCommand.Stop -> {
                flowOf(TimerState.Idle)
            }

            is TimerCommand.Start -> {
                getTimerFlow(command.duration, command.updateInterval)
                    .onCompletion { cause ->
                        if (cause == null) {
                            timerCommand.value = TimerCommand.Stop
                        }
                    }
            }
        }
    }.stateIn(
        scope = applicationScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TimerState.Idle
    )

    companion object {
        private var instance: TimerController? = null
        fun getInstance(scope: CoroutineScope): TimerController {
            return instance ?: synchronized(this) {
                instance ?: CoroutineTimerController(scope).also { instance = it }
            }
        }
    }

    fun getTimerFlow(totalTimeInMillis: Long, updateInterval: Long): Flow<TimerState> = flow {
        val endClockTime = SystemClock.elapsedRealtime() + totalTimeInMillis
        // start the countdown timer off main thread
        while (SystemClock.elapsedRealtime() < endClockTime) {
            emit(TimerState.Running(endClockTime - SystemClock.elapsedRealtime()))
            delay(updateInterval)
        }
    }

    override fun start(totalTimeInMillis: Long, updateInterval: Long) {
        timerCommand.value = TimerCommand.Start(totalTimeInMillis, updateInterval)
    }

    override fun pause() {
        //pause the timer
    }

    override fun stop() {
        timerCommand.value = TimerCommand.Stop
    }

    override fun resume() {
        TODO("resume paused timer")
    }

    sealed class TimerCommand {
        data class Start(val duration: Long, var updateInterval: Long) : TimerCommand()
        object Stop : TimerCommand()
    }
}