package com.devshady.captone.stopwatch

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class TimerApp : Application() {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
}