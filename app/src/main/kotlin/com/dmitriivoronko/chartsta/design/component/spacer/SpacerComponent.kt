package com.dmitriivoronko.chartsta.design.component.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.dmitriivoronko.chartsta.design.theme.AppTheme
import com.dmitriivoronko.chartsta.design.theme.Indents

@Composable
fun SpacerComponent(
    sizeSelector: @Composable Indents.() -> Dp,
) {
    Spacer(modifier = Modifier.size(AppTheme.indents.sizeSelector()))
}