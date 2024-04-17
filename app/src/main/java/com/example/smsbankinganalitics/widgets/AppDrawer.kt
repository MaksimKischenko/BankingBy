package com.example.smsbankinganalitics.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smsbankinganalitics.R
import com.example.smsbankinganalitics.models.Currencies
import com.example.smsbankinganalitics.models.SmsAddress
import com.example.smsbankinganalitics.utils.Localization
import com.example.smsbankinganalitics.view_models.SmsReceiverState
import com.example.smsbankinganalitics.view_models.UiEffectsEvent
import com.example.smsbankinganalitics.view_models.UiEffectsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun AppDrawer(
    smsReceiverState: SmsReceiverState,
    uiEffectsViewModel: UiEffectsViewModel,
    scope: CoroutineScope,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {

    ModalNavigationDrawer(
        modifier = Modifier.clickable {
            uiEffectsViewModel.onEvent(UiEffectsEvent.HideBottomBar(false))
            scope.launch {
                drawerState.close()
            }
        },
        gesturesEnabled = false,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .padding(end = 40.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onTertiary)

                ) {
                    DrawerHeader()
                    Spacer(
                        modifier = Modifier
                            .height(6.dp)
                    )
                    DrawerElement(
                        imageId = R.drawable.bars,
                        header = SmsAddress.BNB_BANK.name,
                        content = "${smsReceiverState.smsCommonInfo?.cardMask}"
                    )
                    DrawerElement(
                        imageId = R.drawable.calendar,
                        header = "${Localization.withComposable(resId = R.string.from)} ${smsReceiverState.smsCommonInfo?.dateFrom}",
                        content = "${Localization.withComposable(resId = R.string.sms_count)} ${smsReceiverState.smsReceivedList?.size}"
                    )
                    DrawerElement(
                        imageId = R.drawable.bars2,
                        header = "${Localization.withComposable(resId = R.string.currency)} ${Currencies.BYN.name}",
                        content = "${Localization.withComposable(resId = R.string.on_card)} ${smsReceiverState.smsCommonInfo?.availableAmount} ${Currencies.BYN.name}"
                    )
                }

            }
        },
        content = content
    )
}