package com.example.smsbankinganalitics.services

import android.graphics.Typeface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.smsbankinganalitics.models.ActionCategory
import com.example.smsbankinganalitics.models.Currencies
import com.example.smsbankinganalitics.models.SmsParsedBody
import com.example.smsbankinganalitics.ui.theme.Palette1
import com.example.smsbankinganalitics.ui.theme.Palette11
import com.example.smsbankinganalitics.ui.theme.Palette12
import com.example.smsbankinganalitics.ui.theme.Palette13
import com.example.smsbankinganalitics.ui.theme.Palette3
import com.example.smsbankinganalitics.ui.theme.Palette4
import com.example.smsbankinganalitics.ui.theme.Palette5
import com.example.smsbankinganalitics.ui.theme.Palette6
import com.example.smsbankinganalitics.ui.theme.Palette7
import com.example.smsbankinganalitics.utils.mapIndexed
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols
import java.time.LocalDateTime
import javax.inject.Inject


class ChartsMaker @Inject constructor() {

    private val colorsPalette by lazy {
        listOf(
            Palette6, Palette7, Palette1, Palette5, Palette11, Palette12, Palette13
        )
    }

    val donutChartConfig by lazy {
        PieChartConfig(
            labelVisible = true,
            labelColor = Palette4,
            labelType = PieChartConfig.LabelType.VALUE,
            labelFontSize = 24.sp,
            isSumVisible = true,
            sumUnit = Currencies.BYN.name,
            backgroundColor = Palette3,
            sliceLabelTextColor = Palette4,
            sliceLabelTextSize = 24.sp,
            sliceLabelTypeface = Typeface.DEFAULT_BOLD,
            isAnimationEnable = true,
            strokeWidth = 100f,
        )
    }

    private fun makeSumByActionCategoriesMap(
        parsedSmsBodies: List<SmsParsedBody>, actionCategories: List<ActionCategory>
    ): Map<String, Double> {
        val totalPaymentSumByTerminalMap = parsedSmsBodies.filter {
            actionCategories.contains(it.actionCategory) && it.paymentCurrency == Currencies.BYN.name
        }.groupBy { it.terminal.associatedName }
            .mapValues { entry -> entry.value.sumOf { it.paymentSum } }

        return totalPaymentSumByTerminalMap
    }


    private fun makeSumByMonthsMap(parsedSmsBodies: List<SmsParsedBody>): Map<String, Double> {
        return parsedSmsBodies.filter {
            it.actionCategory == ActionCategory.PAYMENT && it.paymentDate.year == LocalDateTime.now().year && it.paymentCurrency == Currencies.BYN.name
        }.groupBy { getMonthNameByNumber(it.paymentDate.month.value) }
            .mapValues { entry -> entry.value.sumOf { it.paymentSum } }
    }

    private fun getMonthNameByNumber(monthNumber: Int): String {
        val monthNames = DateFormatSymbols().shortMonths
        return monthNames[monthNumber - 1].uppercase()
    }

    suspend fun makePieChartDataByMonth(parsedSmsBodies: List<SmsParsedBody>): PieChartData? =
        coroutineScope {
            val deferredSlices = CompletableDeferred<List<PieChartData.Slice>>()
            launch(Dispatchers.Default) {
                val mapMonth = makeSumByMonthsMap(parsedSmsBodies)
                val slices = makeSlicesByMonth(mapMonth, colorsPalette)
                deferredSlices.complete(slices)
            }
            val slices = deferredSlices.await()
            return@coroutineScope if (slices.isEmpty()) null
            else PieChartData(slices = slices, plotType = PlotType.Donut)
        }

    private fun makeSlicesByMonth(
        mapCategories: Map<String, Double>, colors: List<Color>
    ): List<PieChartData.Slice> {
        return mapCategories.mapIndexed { index, (month, totalSum) ->
            PieChartData.Slice(
                "$month: ${totalSum.toFloat()} ${Currencies.BYN.name}",
                totalSum.toFloat(),
                colors[index],
            )
        }
    }

    suspend fun makePieChartDataByActionCategories(
        parsedSmsBodies: List<SmsParsedBody>, actionCategories: List<ActionCategory>
    ): PieChartData? = coroutineScope {
        val deferredSlices = CompletableDeferred<List<PieChartData.Slice>>()
        launch(Dispatchers.Default) {
            val mapCategories = makeSumByActionCategoriesMap(parsedSmsBodies, actionCategories)
            val slices = makeSlicesByActionCategories(mapCategories, colorsPalette)
            deferredSlices.complete(slices)
        }
        val slices = deferredSlices.await()
        return@coroutineScope if (slices.isEmpty()) null
        else PieChartData(slices = slices, plotType = PlotType.Donut)
    }

    private fun makeSlicesByActionCategories(
        mapCategories: Map<String, Double>, colors: List<Color>
    ): List<PieChartData.Slice> {
        return mapCategories.mapIndexed { index, (terminalName, totalPaymentSum) ->
            PieChartData.Slice(
                "$terminalName: ${totalPaymentSum.toFloat()} ${Currencies.BYN.name}",
                totalPaymentSum.toFloat(),
                colors[index],
            )
        }
    }
}