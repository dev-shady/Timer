package com.devshady.captone.stopwatch.feature.Countdown

import android.graphics.Color
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class TimerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Required for statusBarsPadding() to work correctly
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.Companion.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.Companion.dark(Color.TRANSPARENT)
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
            window.isStatusBarContrastEnforced = false
        }

        setContent {
            CountdownTimer()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun CountdownTimer() {
        val timerViewModel: TimerViewModel by viewModels()
        val timerUiState = timerViewModel.uiState.collectAsStateWithLifecycle()

        Scaffold(containerColor = androidx.compose.ui.graphics.Color.Companion.Black) { innerPadding ->
            Surface(
                modifier = Modifier.Companion
                    .fillMaxSize()
                    .padding(innerPadding),
                color = androidx.compose.ui.graphics.Color.Companion.Black // Setting background to black so white text is visible
            ) {
                Box(
                    Modifier.Companion.padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = timerUiState.value.formattedTime,
                        color = androidx.compose.ui.graphics.Color.Companion.White,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )

                }

            }
        }

    }
}