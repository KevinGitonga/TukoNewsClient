package ke.co.ipandasoft.tukonewsclient.di

import androidx.room.Room
import ke.co.ipandasoft.tukonewsclient.R
import ke.co.ipandasoft.tukonewsclient.data.local.LocalDataSource
import ke.co.ipandasoft.tukonewsclient.data.local.room.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val persistenceModule = module {

  single {
    Room
      .databaseBuilder(androidApplication(), AppDatabase::class.java,
        androidApplication().getString(R.string.database_name))
      .allowMainThreadQueries()
      .fallbackToDestructiveMigration()
      .build()
  }

  single { get<AppDatabase>().newsCatDao() }

  single { get<AppDatabase>().newsPostDao() }

  single { LocalDataSource(get(),get()) }
}
