package com.devshady.captone.stopwatch.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun TimeInputUnit(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            // Border colors
            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.LightGray,

            // Label colors
            focusedLabelColor = Color.Blue,
            unfocusedLabelColor = Color.Gray,

            // Placeholder and Text colors
            focusedPlaceholderColor = Color.LightGray,
            unfocusedPlaceholderColor = Color.Gray,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,

            // Background (Container)
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        )
    )
}