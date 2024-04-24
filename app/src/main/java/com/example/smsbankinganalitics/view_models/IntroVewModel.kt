package com.example.smsbankinganalitics.view_models
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.model.IntroPageItem
import com.example.smsbankinganalitics.view_models.utils.Localization.withContext
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@HiltViewModel
class IntroVewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {

    private val introHeader1 = context.getString(R.string.introHeader_1)
    private val introDesc1 = context.getString(R.string.introDesc_1)
    private val introHeader2 = context.getString(R.string.introHeader_2)
    private val introDesc2 = context.getString(R.string.introDesc_2)
    private val introHeader3 = context.getString(R.string.introHeader_3)
    private val introDesc3 = context.getString(R.string.introDesc_3)

    private val introItems = listOf(
        IntroPageItem(R.drawable.sms_search, introHeader1, introDesc1),
        IntroPageItem(R.drawable.transaction, introHeader2, introDesc2),
        IntroPageItem(R.drawable.analitics, introHeader3, introDesc3)
    )

    var state by mutableStateOf(IntroState(introItems))
}

data class IntroState(
    val introItems: List<IntroPageItem>
)
