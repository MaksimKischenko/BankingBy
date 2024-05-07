package com.production.smsbankinganalitics.view_models.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

object Localization {
    fun withContext(context: Context, resId:Int): String {
        return context.getString(resId)
    }
    @Composable
    fun withComposable(resId:Int): String {
        return stringResource(id = resId)
    }
}

