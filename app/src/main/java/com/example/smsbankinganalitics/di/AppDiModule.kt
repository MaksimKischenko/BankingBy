package com.example.smsbankinganalitics.di

import android.content.Context
import com.example.smsbankinganalitics.data.PreferencesManager
import com.example.smsbankinganalitics.data.repositories.SmsRepository
import com.example.smsbankinganalitics.services.SMSParser.SmsBnbParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDiModule {
    @Provides
    @Singleton
    fun provideContextPreferencesModule(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Provides
    @Singleton
    fun providePreferences(context: Context): PreferencesManager {
        return PreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideSmsRepository(): SmsRepository {
        return SmsRepository()
    }

    @Provides
    @Singleton
    fun provideSmsBnbParser(context: Context): SmsBnbParser {
        return SmsBnbParser(context)
    }
}

//@Module
//@InstallIn(ActivityComponent::class)
//abstract class SmsParserModule {
//
//    @Binds
//    abstract fun bindSmsParserBnb(
//        smsBnbParser: SmsBnbParser
//    ): SmsParser
//}