package com.example.smsbankinganalitics.models

import com.example.smsbankinganalitics.R


sealed class BottomNavBarItem(val resId: Int, val iconId:Int, val route: String) {
    data object SmsBankingItem: BottomNavBarItem(R.string.sms_banking, R.drawable.sms, "/sms_banking")
    data object OperationsItem: BottomNavBarItem(R.string.operations, R.drawable.money_cash, "/money_cash")
    data object SettingsItem: BottomNavBarItem(R.string.settings, R.drawable.settings_gear, "/settings")
}
