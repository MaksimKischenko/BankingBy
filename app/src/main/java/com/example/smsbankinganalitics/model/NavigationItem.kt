package com.example.smsbankinganalitics.model

import com.example.smsbankinganalitics.R


sealed class BottomNavBarItem(val resId: Int, val iconId:Int, val route: String) {
    data object SmsBankingItem: BottomNavBarItem(R.string.sms_banking, R.drawable.sms, Navigation.SmsBanking.route)
    data object OperationsItem: BottomNavBarItem(R.string.operations, R.drawable.money_cash, Navigation.Analytics.route)
    data object SettingsItem: BottomNavBarItem(R.string.settings, R.drawable.settings_gear, Navigation.Settings.route)
}

sealed class Navigation(val route: String) {
    data object Splash: Navigation( "/")
    data object Intro: Navigation( "/intro")
    data object SmsBanking: Navigation( "/sms_banking")
    data object Analytics: Navigation( "/analytics")
    data object Settings: Navigation( "/settings")
}
