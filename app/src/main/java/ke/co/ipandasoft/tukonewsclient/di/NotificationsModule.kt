package ke.co.ipandasoft.tukonewsclient.di

import android.app.Application
import androidx.work.*
import ke.co.ipandasoft.tukonewsclient.TukoNewsApp
import ke.co.ipandasoft.tukonewsclient.ui.activity.notifications.NotificationRequestWorker
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val notificationModule = module {

    single {
        Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.CONNECTED)
            setRequiresCharging(false)
            setRequiresStorageNotLow(false)
            setRequiresBatteryNotLow(true)
        }.build()
    }

}