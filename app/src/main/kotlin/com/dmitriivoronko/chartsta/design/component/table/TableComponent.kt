@file:OptIn(ExperimentalFoundationApi::class)

package com.dmitriivoronko.chartsta.design.component.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dmitriivoronko.chartsta.design.theme.AppTheme

@Composable
fun TableComponent(
    column1Title: String,
    column2Title: String,
    data: List<Pair<String, String>>,
    modifier: Modifier = Modifier,
) {
    val column1Weight = .5f
    val column2Weight = .5f

    LazyColumn(
        contentPadding = PaddingValues(AppTheme.indents.x2),
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
    ) {
        stickyHeader {
            Row(Modifier.background(MaterialTheme.colorScheme.outline)) {
                TableCell(text = column1Title, weight = column1Weight)
                TableCell(text = column2Title, weight = column2Weight)
            }
        }

        items(data.size) {
            val (text1, text2) = data[it]
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = text1, weight = column1Weight)
                TableCell(text = text2, weight = column2Weight)
            }
        }
    }
}

@Composable
private fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        modifier = Modifier
            .border(AppTheme.indents.x0_125, MaterialTheme.colorScheme.outlineVariant)
            .weight(weight)
            .padding(AppTheme.indents.x1)
    )
}

@Composable
@Preview
fun TableComponentPreview() {
    AppTheme {
        Scaffold { paddingValues ->
            val data = (1..25).mapIndexed { index, item ->
                index.toString() to "Item $index"
            }

            TableComponent(
                column1Title = "Column 1",
                column2Title = "Column 2",
                data = data,
                modifier = Modifier.padding(paddingValues),
            )
        }
    }
}