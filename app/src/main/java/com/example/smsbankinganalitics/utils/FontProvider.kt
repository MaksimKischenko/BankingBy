package com.example.smsbankinganalitics.utils

import androidx.compose.ui.text.googlefonts.GoogleFont
import com.example.smsbankinganalitics.R

object FontProvider {
    fun getCertificateProvider(): GoogleFont.Provider {
        return GoogleFont.Provider(
            providerAuthority = "com.google.android.gms.fonts",
            providerPackage = "com.google.android.gms",
            certificates = R.array.com_google_android_gms_fonts_certs
        )
    }

}