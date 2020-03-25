package ke.co.ipandasoft.tukonewsclient

import android.content.Context
import androidx.multidex.MultiDexApplication
import ke.co.ipandasoft.tukonewsclient.di.networkModule
import ke.co.ipandasoft.tukonewsclient.di.persistenceModule
import ke.co.ipandasoft.tukonewsclient.di.repositoryModule
import ke.co.ipandasoft.tukonewsclient.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import kotlin.properties.Delegates



class TukoNewsApp : MultiDexApplication(){


    companion object {
        private val TAG = "Tuko News"
        var context: Context by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initLogger()
        initKoinDi()


    }

    open fun initKoinDi() {
        startKoin {
            androidContext(this@TukoNewsApp)
            modules(persistenceModule)
            modules(repositoryModule)
            modules(viewModelModule)
            modules(networkModule)
        }

    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }



}
