package com.dmitriivoronko.chartsta.feature.explore.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmitriivoronko.chartsta.R
import com.dmitriivoronko.chartsta.design.component.spacer.SpacerComponent
import com.dmitriivoronko.chartsta.design.component.table.TableComponent
import com.dmitriivoronko.chartsta.design.theme.AppTheme
import com.dmitriivoronko.chartsta.feature.explore.domain.model.Error
import com.dmitriivoronko.chartsta.feature.explore.domain.model.Point
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.horizontal.topAxis
import com.patrykandpatrick.vico.compose.axis.vertical.endAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.ChartScrollState
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.m3.style.m3ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import java.io.IOException

@Composable
fun ExploreScreen(
    isProgress: Boolean,
    error: Error?,
    points: List<Point>,
    onRetryClick: () -> Unit,
) {
    Scaffold { contentPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
        ) {
            val tableData by remember(points) {
                derivedStateOf {
                    points.map { point ->
                        point.x.toString() to point.y.toString()
                    }
                }
            }

            val chartEntryModel by remember(points) {
                derivedStateOf {
                    points
                        .map { point -> entryOf(point.x, point.y) }
                        .let { floatEntries -> entryModelOf(floatEntries) }
                }
            }

            val tableState = rememberLazyListState()
            val chartScrollState = rememberChartScrollState()

            if (this.maxWidth < 400.dp) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    if (isProgress) {
                        ProgressBlock()
                    } else if (error != null) {
                        ErrorBlock(onRetryClick)
                    } else {
                        ContentBlock(
                            tableData,
                            chartEntryModel,
                            Modifier.weight(1f),
                            tableState,
                            chartScrollState,
                        )
                    }
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    if (isProgress) {
                        ProgressBlock()
                    } else if (error != null) {
                        ErrorBlock(onRetryClick)
                    } else {
                        ContentBlock(
                            tableData,
                            chartEntryModel,
                            Modifier.weight(1f),
                            tableState,
                            chartScrollState,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorBlock(
    onRetryClick: () -> Unit,
) {
    Text(
        text = stringResource(id = R.string.explore_screen_error_label),
        style = MaterialTheme.typography.headlineSmall,
    )
    SpacerComponent { x2 }
    ElevatedButton(
        onClick = onRetryClick,
        modifier = Modifier
            .padding(horizontal = AppTheme.indents.x2)
            .fillMaxWidth(),
    ) {
        Text(
            text = stringResource(id = R.string.explore_screen_retry_action),
        )
    }
}

@Composable
private fun ProgressBlock() {
    Text(
        text = stringResource(id = R.string.explore_screen_progress_label),
        style = MaterialTheme.typography.headlineSmall,
    )
    SpacerComponent { x2 }
    CircularProgressIndicator()
}

@Composable
private fun ContentBlock(
    tableData: List<Pair<String, String>>,
    chartEntryModel: ChartEntryModel,
    childrenModifier: Modifier,
    tableState: LazyListState = rememberLazyListState(),
    chartScrollState: ChartScrollState = rememberChartScrollState(),
) {
    TableComponent(
        column1Title = "x",
        column2Title = "y",
        data = tableData,
        modifier = childrenModifier,
        state = tableState,
    )

    ProvideChartStyle(
        chartStyle = m3ChartStyle(),
    ) {
        Chart(
            chart = lineChart(),
            model = chartEntryModel,
            startAxis = startAxis(),
            bottomAxis = bottomAxis(),
            topAxis = topAxis(),
            endAxis = endAxis(),
            modifier = childrenModifier,
            chartScrollState = chartScrollState,
        )
    }
}

@Preview(
    group = "CONTENT",
)
@Composable
fun ExploreScreenContentPreview() {
    AppTheme {
        ExploreScreen(
            isProgress = false,
            error = null,
            points = examplePoints,
            onRetryClick = {},
        )
    }
}

@Preview(
    device = "spec:parent=pixel_5,orientation=landscape",
    group = "CONTENT",
)
@Composable
fun ExploreScreenContentLandscapePreview() {
    AppTheme {
        ExploreScreen(
            isProgress = false,
            error = null,
            points = examplePoints,
            onRetryClick = {},
        )
    }
}

@Preview
@Composable
fun ExploreScreenProgressPreview() {
    AppTheme {
        ExploreScreen(
            isProgress = true,
            error = null,
            points = emptyList(),
            onRetryClick = {},
        )
    }
}

@Preview
@Composable
fun ExploreScreenErrorPreview() {
    AppTheme {
        ExploreScreen(
            isProgress = false,
            error = Error.NetworkError(IOException()),
            points = emptyList(),
            onRetryClick = {}
        )
    }
}