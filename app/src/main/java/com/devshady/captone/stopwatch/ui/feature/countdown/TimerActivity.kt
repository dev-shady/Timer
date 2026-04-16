package com.devshady.captone.stopwatch.ui.feature.countdown

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devshady.captone.stopwatch.ui.components.CountdownTimerScreen
import android.graphics.Color as JavaColor

class TimerActivity : ComponentActivity() {
    val timerViewModel: TimerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Required for statusBarsPadding() to work correctly
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(JavaColor.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(JavaColor.TRANSPARENT)
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        setContent {
            TimerScreen()
        }
    }

    @Composable
    fun TimerScreen() {
        val timerUiState = timerViewModel.uiState.collectAsStateWithLifecycle().value

        Scaffold(containerColor = androidx.compose.ui.graphics.Color.Black) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = Color.Black // Setting background to black so white text is visible
            ) {
                Box(
                    Modifier.padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CountdownTimerScreen(
                        timerUiState.timerState,
                        timerUiState.formattedTime
                    ) {
                        timerViewModel.start(it)
                    }
                }

            }
        }

    }
}