package com.production.smsbankinganalitics.view_models.utils

import androidx.navigation.NavHostController
import com.production.smsbankinganalitics.R




sealed class Navigation(val route: String) {
    data object Splash: Navigation( "/")
    data object Intro: Navigation( "/intro")
    data object SmsBanking: Navigation( "/sms_banking")
    data object Analytics: Navigation( "/analytics")
    data object Settings: Navigation( "/settings")
    companion object {

        fun goToSplash(navHostController: NavHostController) {
            navHostController.navigate(Splash.route) {
                popUpTo(0) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }

        fun goToIntro(navHostController: NavHostController) {
            navHostController.navigate(Intro.route) {
                popUpTo(0) {
                    inclusive = true
                }
            }
        }

        fun goToSmsBanking(navHostController: NavHostController, isFirstLoad: Boolean) {
            navHostController.navigate(SmsBanking.route) {
                if (isFirstLoad) {
                    popUpTo(0) {
                        inclusive = true
                    }
                } else {
                    popUpTo(SmsBanking.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        }
        fun goToAnalytics(navHostController: NavHostController) {
            navHostController.navigate(Analytics.route) {
                popUpTo(Analytics.route) {
                    inclusive = true
                }
            }
        }
        fun goToSettings(navHostController: NavHostController, isFirstLoad: Boolean) {
            navHostController.navigate(Settings.route) {
                if (isFirstLoad) {
                    popUpTo(0) {
                        inclusive = false
                    }
                } else {
                    popUpTo(Settings.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        }
    }
}




sealed class BottomNavBarItem(val resId: Int, val iconId:Int, val route: String) {
    data object SmsBankingItem: BottomNavBarItem(R.string.sms_banking, R.drawable.sms_search, Navigation.SmsBanking.route)
    data object OperationsItem: BottomNavBarItem(R.string.operations, R.drawable.money_cash, Navigation.Analytics.route)
    data object SettingsItem: BottomNavBarItem(R.string.settings, R.drawable.settings_gear, Navigation.Settings.route)
}

