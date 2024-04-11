package com.example.smsbankinganalitics.view_models

import android.graphics.Typeface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.smsbankinganalitics.data.repositories.SmsRepository
import com.example.smsbankinganalitics.models.ActionCategory
import com.example.smsbankinganalitics.models.Currencies
import com.example.smsbankinganalitics.models.SmsParsedBody
import com.example.smsbankinganalitics.services.SMSParser.SmsBnbParser
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val smsParser: SmsBnbParser, private val smsRepository: SmsRepository
) : ViewModel() {

    var state by mutableStateOf(AnalyticsState())

    private val colorsPalette =
        listOf(
            Palette6,
            Palette7,
            Palette1,
            Palette5,
            Palette11,
            Palette12,
            Palette13
        )


    private val donutChartConfig =
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

    suspend fun onEvent(event: AnalyticsEvent) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                when (event) {
                    AnalyticsEvent.ByActionCategories -> {
                        state = state.copy(isLoading = true, paymentPieChartDataMap = mapOf(
                            "Затраты" to null
                        ))
                        val noParsedSmsMap = smsRepository.getSmsMap()
                        val parsedSmsBodies = filterAndParse(noParsedSmsMap)
                        val paymentPieChartData =
                            makePieChartData(parsedSmsBodies, listOf(ActionCategory.PAYMENT))
                        val transfersPieChartData =  makePieChartData(
                            parsedSmsBodies,
                            listOf(ActionCategory.TRANSFER_TO, ActionCategory.TRANSFER_FROM)
                        )
                        state =
                            state.copy(
                                isLoading = false,
                                donutChartConfig = donutChartConfig,
                                paymentPieChartDataMap = mapOf(
                                    "Затраты" to paymentPieChartData,
                                    "Списания и зачисления" to transfersPieChartData
                                )
                            )
                    }
                }

            } catch (e: Exception) {
                state = state.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }

    private fun filterAndParse(noParsedSmsMap: Map<String, LocalDateTime>): List<SmsParsedBody> {
        return noParsedSmsMap.map { smsEntry -> smsParser.toParsedSMSBody(smsEntry, true) }
    }

    private suspend fun makePieChartData(
        parsedSmsBodies: List<SmsParsedBody>,
        actionCategories: List<ActionCategory>
    ): PieChartData {
        val deferredSlices = CompletableDeferred<List<PieChartData.Slice>>()
        viewModelScope.launch(Dispatchers.Unconfined) {

            val mapCategories = makeByActionCategoriesMap(parsedSmsBodies, actionCategories)
            val slices = makeSlices(mapCategories, colorsPalette)
            deferredSlices.complete(slices)
        }

        val slices = deferredSlices.await()
        return PieChartData(slices = slices, plotType = PlotType.Donut)
    }

    private fun makeByActionCategoriesMap(
        parsedSmsBodies: List<SmsParsedBody>,
        actionCategories: List<ActionCategory>
    ): Map<String, Double> {
        val totalPaymentSumByTerminalMap = parsedSmsBodies.filter {
            actionCategories.contains(it.actionCategory) &&
                    it.paymentCurrency == Currencies.BYN.name
        }
            .groupBy { it.terminal.associatedName }
            .mapValues { entry -> entry.value.sumOf { it.paymentSum } }

        return totalPaymentSumByTerminalMap
    }

    private fun makeSlices(
        mapCategories: Map<String, Double>,
        colors: List<Color>
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


sealed class AnalyticsEvent {
    data object ByActionCategories : AnalyticsEvent()
}


data class AnalyticsState(
    val isLoading: Boolean = false,
    var errorMessage: String? = null,
    val donutChartConfig: PieChartConfig? = null,
    val paymentPieChartDataMap: Map<String, PieChartData?>  = emptyMap()
)