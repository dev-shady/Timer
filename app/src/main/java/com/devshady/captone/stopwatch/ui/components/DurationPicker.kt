package com.devshady.captone.stopwatch.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun DurationPicker(
    onSubmit: (Long) -> Unit
) {

    var hours by remember { mutableStateOf("00") }
    var minutes by remember { mutableStateOf("00") }
    var seconds by remember { mutableStateOf("00") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TimeInputUnit(hours, "HH", Modifier.weight(1f)) { hours = it }
            TimeInputUnit(minutes, "MM", Modifier.weight(1f)) { minutes = it }
            TimeInputUnit(seconds, "SS", Modifier.weight(1f)) { seconds = it }
        }
        Spacer(Modifier.height(4.dp))
        Button(
            onClick = {

                val hrs = hours.toLongOrNull() ?: 0
                val mins = minutes.toLongOrNull() ?: 0
                val secs = seconds.toLongOrNull() ?: 0
                when {
                    hours.length > 2 || minutes.length > 2 || seconds.length > 2 -> {
                        Toast.makeText(context, "Max 2 digits in a field", Toast.LENGTH_SHORT)
                            .show()
                    }

                    mins > 59 || secs > 59 -> {
                        Toast.makeText(
                            context,
                            "Minutes/Seconds must be under 60",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    // Check if user entered nothing (0 total time)
                    hrs == 0L && mins == 0L && secs == 0L -> {
                        Toast.makeText(context, "Please set a duration", Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        onSubmit(hrs * 3600 + mins * 60 + secs)
                    }

                }

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            )

        ) {
            Text("Start")
        }
    }

}