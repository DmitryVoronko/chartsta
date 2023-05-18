package com.dmitriivoronko.chartsta.feature.input.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.dmitriivoronko.chartsta.R
import com.dmitriivoronko.chartsta.design.component.input.number.NumberInputComponent
import com.dmitriivoronko.chartsta.design.component.spacer.SpacerComponent
import com.dmitriivoronko.chartsta.design.theme.AppTheme

@Composable
fun PointCountInputScreen(
    pointCount: String,
    onPointCountChange: (String) -> Unit,
    onGoClick: () -> Unit,
) {
    Scaffold { contentPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {

            val focusRequester = remember { FocusRequester() }

            NumberInputComponent(
                value = pointCount,
                onValueChange = onPointCountChange,
                label = {
                    Text(
                        text = stringResource(id = R.string.point_count_input_screen_input_label),
                    )
                },
                keyboardActions = KeyboardActions(
                    onGo = { onGoClick() },
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Go,
                    keyboardType = KeyboardType.Number,
                ),
                modifier = Modifier
                    .focusRequester(focusRequester)
            )

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }

            SpacerComponent { x2 }

            OutlinedButton(
                onClick = onGoClick,
                modifier = Modifier
                    .padding(horizontal = AppTheme.indents.x2)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.point_count_input_screen_go_action),
                )
            }
        }
    }
}

@Preview
@Composable
fun PointCountInputScreenPreview() {
    AppTheme {
        var pointCountState by remember {
            mutableStateOf("")
        }

        PointCountInputScreen(
            pointCount = pointCountState,
            onPointCountChange = { newPointCount ->
                pointCountState = newPointCount
            },
            onGoClick = {},
        )
    }
}