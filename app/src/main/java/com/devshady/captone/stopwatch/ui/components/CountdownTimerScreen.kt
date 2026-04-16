package com.devshady.captone.stopwatch.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.devshady.captone.stopwatch.domain.TimerState

@Composable
fun CountdownTimerScreen(
    uiState: TimerState,
    formattedTime: String,
    onSubmit: (Long) -> Unit
) {

    when (uiState) {
        TimerState.Idle, TimerState.Finished -> {

            DurationPicker {
                onSubmit(it)
            }
        }

        is TimerState.Running -> {
            Text(
                text = formattedTime,
                color = androidx.compose.ui.graphics.Color.Companion.White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
        }

        else -> {
            // TODO add checks for pause, reset state
            DurationPicker {
                onSubmit(it)
            }
        }
    }

}