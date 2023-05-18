package com.dmitriivoronko.chartsta.design.component.input.number

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.isDigitsOnly
import com.dmitriivoronko.chartsta.design.theme.AppTheme

@Composable
fun NumberInputComponent(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.isDigitsOnly()) {
                onValueChange(newValue)
            }
        },
        label = label,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        modifier = Modifier
            .padding(horizontal = AppTheme.indents.x2)
            .fillMaxWidth()
            .then(modifier),
    )
}

@Preview
@Composable
fun NumberInputComponentPreview() {
    AppTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            var numberInputState by remember {
                mutableStateOf("")
            }

            NumberInputComponent(
                value = numberInputState,
                onValueChange = { newValue ->
                    numberInputState = newValue
                },
            )
        }
    }
}

