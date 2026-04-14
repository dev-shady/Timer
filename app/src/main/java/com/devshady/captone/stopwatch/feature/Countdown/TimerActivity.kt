package com.devshady.captone.stopwatch.feature.Countdown

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class TimerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Required for statusBarsPadding() to work correctly
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.Companion.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.Companion.dark(Color.TRANSPARENT)
        )

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
            window.isStatusBarContrastEnforced = false
        }

        setContent {
            Welcome()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Welcome() {
        Scaffold(containerColor = androidx.compose.ui.graphics.Color.Companion.Black) { innerPadding ->
            Surface(
                modifier = Modifier.Companion.fillMaxSize().padding(innerPadding),
                color = androidx.compose.ui.graphics.Color.Companion.Black // Setting background to black so white text is visible
            ) {
                Box(Modifier.Companion.padding(16.dp)) {
                    Text(
                        text = "Hello",
                        color = androidx.compose.ui.graphics.Color.Companion.White
                    )
                }
            }
        }

    }
}