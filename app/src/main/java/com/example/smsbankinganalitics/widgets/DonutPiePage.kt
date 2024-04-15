package com.example.smsbankinganalitics.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.components.Legends
import co.yml.charts.common.model.LegendsConfig
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.utils.Localization


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DonutPiePage(
    pieChartData: PieChartData,
    dateFrom: String,
    donutChartConfig: PieChartConfig,
    graphicsLayer: GraphicsLayerScope.() -> Unit,
    context: Context = LocalContext.current
) {
    Card(
        Modifier
            .fillMaxSize()
            .graphicsLayer {
                graphicsLayer.invoke(this)
            }

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onTertiary)
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 4.dp),
                text = Localization.withComposable(resId = R.string.from) + " " + dateFrom,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            pieChartData.let {
                DataUtils.getLegendsConfigFromPieChartData(
                    pieChartData = it,
                    6
                )
            }.let {
                Legends(
                    legendsConfig = LegendsConfig(
                        colorBoxSize = 20.dp,
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.tertiary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        legendLabelList = it.legendLabelList,
                        legendsArrangement = Arrangement.Start
                    )
                )
            }
            ElevatedCard(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onTertiary)
                    .fillMaxSize(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp,
                ),
                shape = RoundedCornerShape(16.dp),
            ) {
                DonutPieChart(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.tertiary)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    pieChartData,
                    donutChartConfig
                ) { slice ->
                    Toast.makeText(context, slice.label, Toast.LENGTH_SHORT).show()
                }
            }

        }                // Card content
    }
}