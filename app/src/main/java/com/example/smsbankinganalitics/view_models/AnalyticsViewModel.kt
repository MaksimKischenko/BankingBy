package com.example.smsbankinganalitics.view_models

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.data.repositories.SmsRepository
import com.example.smsbankinganalitics.models.ActionCategory
import com.example.smsbankinganalitics.models.SmsParsedBody
import com.example.smsbankinganalitics.services.ChartsMaker
import com.example.smsbankinganalitics.services.SMSParser.SmsBnbParser
import com.example.smsbankinganalitics.utils.DateUtils
import com.example.smsbankinganalitics.utils.Localization.withContext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject



@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val smsParser: SmsBnbParser,
    private val smsRepository: SmsRepository,
    private val chartsMaker: ChartsMaker
) : ViewModel() {

    var state by mutableStateOf(AnalyticsState())


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

                        state = state.copy(
                            isLoading = false,
                            donutChartConfig = chartsMaker.donutChartConfig,
                            dateFrom = dateFrom,
                            paymentPieChartDataMap = mapOf(
                                withContext(event.context, R.string.expenses) to paymentPieChartData,
                                withContext(event.context, R.string.month_stat) to monthStatisticPieChartData,
                                withContext(event.context, R.string.writeOff_credits) to transfersPieChartData,
                            )
                        )
                    }
                }

            } catch (e: Exception) {
                state = state.copy(isLoading = false, errorMessage = e.message)
            }
        }
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
        return noParsedSmsMap.map { smsEntry -> smsParser.toParsedSmsBody(smsEntry, true) }
    }
}


sealed class AnalyticsEvent {
    data class ByActionCategories(
        val context: Context
    ) : AnalyticsEvent()
}


data class AnalyticsState(
    val isLoading: Boolean = false,
    var errorMessage: String? = null,
    val donutChartConfig: PieChartConfig? = null,
    val dateFrom: String? = null,
    val paymentPieChartDataMap: Map<String, PieChartData?> = emptyMap()
)