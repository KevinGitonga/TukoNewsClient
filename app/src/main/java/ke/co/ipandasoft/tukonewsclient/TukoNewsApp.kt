package ke.co.ipandasoft.tukonewsclient

import android.content.Context
import androidx.multidex.MultiDexApplication
import ke.co.ipandasoft.tukonewsclient.di.networkModule
import ke.co.ipandasoft.tukonewsclient.di.persistenceModule
import ke.co.ipandasoft.tukonewsclient.di.repositoryModule
import ke.co.ipandasoft.tukonewsclient.di.viewModelModule
import org.adblockplus.libadblockplus.android.AdblockEngine
import org.adblockplus.libadblockplus.android.SingleInstanceEngineProvider
import org.adblockplus.libadblockplus.android.settings.AdblockHelper
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
        initAdBlocker()
        initKoinDi()

    }

    private fun initAdBlocker() {

        if (!AdblockHelper.get().isInit())
        {
            // init Adblock
            val basePath = getDir(AdblockEngine.BASE_PATH_DIRECTORY, Context.MODE_PRIVATE).getAbsolutePath()

            AdblockHelper
                .get()
                .init(this, basePath, true, AdblockHelper.PREFERENCE_NAME)
                .addEngineCreatedListener(engineCreatedListener)
                .addEngineDisposedListener(engineDisposedListener)
        }
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


    object engineCreatedListener : SingleInstanceEngineProvider.EngineCreatedListener {
        override fun onAdblockEngineCreated(engine: AdblockEngine?) {
            Timber.e("ENGINE INITIATED")
        }

    }

    object engineDisposedListener : SingleInstanceEngineProvider.EngineDisposedListener {
        override fun onAdblockEngineDisposed() {
            Timber.e("ENGINE DISPOSED")
        }

    }

}


