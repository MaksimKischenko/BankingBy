package com.production.smsbankinganalitics

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.production.smsbankinganalitics.view_models.data.PreferencesManager
import com.production.smsbankinganalitics.view_models.data.repositories.SmsRepository
import com.production.smsbankinganalitics.view_models.services.ChartsMaker
import com.production.smsbankinganalitics.view_models.services.SmsBroadcastReceiver
import com.production.smsbankinganalitics.view_models.services.SmsParsers.SmsParserFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Qualifier
//@Retention(AnnotationRetention.BINARY)
//annotation class Impl1
//
//@Qualifier
//@Retention(AnnotationRetention.BINARY)
//annotation class Impl2
//private const val BNB = "BNB"
//private const val ASB = "ASB"
//@Module
//@InstallIn(ActivityComponent::class)
//abstract class AnalyticsModule {
//    @Binds
//    @Impl1
//    abstract fun bindAnalyticsService1(analyticsServiceImpl: AnalyticsServiceImpl): AnalyticsService
//
//    @Binds
//    @Impl2
//    abstract fun bindAnalyticsService2(analyticsServiceImpl: AnalyticsServiceImpl2): AnalyticsService
//}
//
//@HiltViewModel
//class AnalyticsViewModel @Inject constructor(
//    @Impl1 private val analyticsService1: AnalyticsService,
//    @Impl2 private val analyticsService2: AnalyticsService
//) : ViewModel() {
//    // Ваш код ViewModel здесь
//}
//




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

//    @Provides
//    @Singleton
//    @Named(BNB)
//    fun provideSmsBnbParser(context: Context): SmsParser {
//        return SmsBnbParser(context)
//    }
//
//    @Provides
//    @Singleton
//    @Named(ASB)
//    fun provideSmsAsbParser(context: Context): SmsParser {
//        return SmsAsbParser(context)
//    }

    @Provides
    @Singleton
    fun provideSmsParserFactory(context: Context): SmsParserFactory {
        return SmsParserFactory(context)
    }



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Provides
    @Singleton
    fun provideSmsBroadcastReceiver(context: Context): SmsBroadcastReceiver {
        return SmsBroadcastReceiver(context)
    }

    @Provides
    @Singleton
    fun provideChartsMaker(): ChartsMaker {
        return ChartsMaker()
    }
}

