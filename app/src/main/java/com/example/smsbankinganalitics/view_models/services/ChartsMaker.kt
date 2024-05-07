package com.example.smsbankinganalitics.view_models.services

import android.graphics.Typeface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.smsbankinganalitics.model.ActionCategory
import com.example.smsbankinganalitics.model.Currencies
import com.example.smsbankinganalitics.model.SmsParsedBody
import com.example.smsbankinganalitics.view.theme.Palette10
import com.example.smsbankinganalitics.view.theme.Palette11
import com.example.smsbankinganalitics.view.theme.Palette13
import com.example.smsbankinganalitics.view.theme.Palette15
import com.example.smsbankinganalitics.view.theme.Palette5
import com.example.smsbankinganalitics.view.theme.Palette7
import com.example.smsbankinganalitics.view.theme.Palette9
import com.example.smsbankinganalitics.view_models.utils.mapIndexed
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
            Palette10, Palette7, Palette9, Palette5, Palette11, Palette15, Palette13
        )
    }

    val donutChartConfig by lazy {
        PieChartConfig(
            labelVisible = true,
            labelType = PieChartConfig.LabelType.VALUE,
            labelFontSize = 24.sp,
            isSumVisible = true,
            sumUnit = Currencies.BYN.name,
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
        else PieChartData(slices = slices, plotType = PlotType.Wave)
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