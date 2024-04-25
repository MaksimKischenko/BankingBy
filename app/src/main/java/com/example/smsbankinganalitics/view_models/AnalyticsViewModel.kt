package com.example.smsbankinganalitics.view_models

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.view_models.data.repositories.SmsRepository
import com.example.smsbankinganalitics.model.ActionCategory
import com.example.smsbankinganalitics.model.SmsAddress
import com.example.smsbankinganalitics.model.SmsArgs
import com.example.smsbankinganalitics.model.SmsParsedBody
import com.example.smsbankinganalitics.view_models.services.ChartsMaker
import com.example.smsbankinganalitics.view_models.services.SmsParsers.SmsBnbParser
import com.example.smsbankinganalitics.view_models.services.SmsParsers.SmsParser
import com.example.smsbankinganalitics.view_models.services.SmsParsers.SmsParserFactory
import com.example.smsbankinganalitics.view_models.utils.DateUtils
import com.example.smsbankinganalitics.view_models.utils.Localization.withContext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject



@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    smsParserFactory: SmsParserFactory,

    private val smsRepository: SmsRepository,
    private val chartsMaker: ChartsMaker,

    ) : ViewModel() {


    var state by mutableStateOf(AnalyticsState())
    private lateinit var smsParser: SmsParser
    private val factory = smsParserFactory


    suspend fun onEvent(event: AnalyticsEvent) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                when (event) {
                    is AnalyticsEvent.ByActionCategories -> {
                        state = state.copy(
                            isLoading = true, paymentPieChartDataMap = mapOf(
                                withContext(event.context, R.string.expenses) to null
                            )
                        )
                        smsParser = factory.setParserBankType(event.smsAddress)
                        val noParsedSmsMap = smsRepository.getAll()
                        val parsedSmsBodies = filterAndParse(noParsedSmsMap)
                        val dateFrom = findOldestDate(parsedSmsBodies)
                        val paymentPieChartData =
                            chartsMaker.makePieChartDataByActionCategories(parsedSmsBodies, listOf(ActionCategory.PAYMENT))
                        val transfersPieChartData = chartsMaker.makePieChartDataByActionCategories(
                            parsedSmsBodies,
                            listOf(ActionCategory.TRANSFER_TO, ActionCategory.TRANSFER_FROM)
                        )
                        val monthStatisticPieChartData = chartsMaker.makePieChartDataByMonth(parsedSmsBodies)

                        val paymentPieChartDataMap = makePaymentPieChartDataMap(
                            paymentPieChartData,
                            transfersPieChartData,
                            monthStatisticPieChartData,
                            event
                        )
                        state = state.copy(
                            isLoading = false,
                            donutChartConfig = chartsMaker.donutChartConfig,
                            dateFrom = dateFrom,
                            paymentPieChartDataMap = paymentPieChartDataMap
                        )
                    }
                }

            } catch (e: Exception) {
                state = state.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }


    private fun makePaymentPieChartDataMap(
        paymentPieChartData: PieChartData?,
        transfersPieChartData: PieChartData?,
        monthStatisticPieChartData: PieChartData?,
        event: AnalyticsEvent.ByActionCategories
    ): MutableMap<String, PieChartData?> {
        val paymentPieChartDataMap = mutableMapOf<String, PieChartData?>()

        paymentPieChartData?.let {
            paymentPieChartDataMap[withContext(event.context, R.string.expenses)] = it
        }
        monthStatisticPieChartData?.let {
            paymentPieChartDataMap[withContext(event.context, R.string.month_stat)] = it
        }
        transfersPieChartData?.let {
            paymentPieChartDataMap[withContext(event.context, R.string.writeOff_credits)] = it
        }
        return paymentPieChartDataMap
    }

    private fun findOldestDate(parsedSmsBodies: List<SmsParsedBody>): String? {
        val oldestSmsParsedBody =
            parsedSmsBodies.filter { true }.minByOrNull { it.paymentDate }?.paymentDate
        return oldestSmsParsedBody?.let {
            DateUtils.fromLocalDateTimeToStringDate(
                it
            )
        }

    }

    private fun filterAndParse(noParsedSmsMap: Map<String, LocalDateTime>): List<SmsParsedBody> {
        return noParsedSmsMap.map { smsEntry ->
            smsParser.toParsedSmsBody(smsEntry, true)
        }
    }
}


sealed class AnalyticsEvent {
    data class ByActionCategories(
        val context: Context,
        val smsAddress: SmsAddress
    ) : AnalyticsEvent()
}


data class AnalyticsState(
    val isLoading: Boolean = false,
    var errorMessage: String? = null,
    val donutChartConfig: PieChartConfig? = null,
    val dateFrom: String? = null,
    val paymentPieChartDataMap: Map<String, PieChartData?> = emptyMap()
)