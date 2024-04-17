package com.example.smsbankinganalitics.models

import com.example.smsbankinganalitics.R


sealed class NavBarItem(val resId: Int, val iconId:Int, val route: String) {
    data object SmsBankingItem: NavBarItem(R.string.sms_banking, R.drawable.sms, "/sms_banking")
    data object OperationsItem: NavBarItem(R.string.operations, R.drawable.money_cash, "/money_cash")
    data object SettingsItem: NavBarItem(R.string.settings, R.drawable.settings_gear, "/settings")
}
