package ke.co.ipandasoft.tukonewsclient.di

import ke.co.ipandasoft.tukonewsclient.data.repository.DataRepository
import org.koin.dsl.module

val repositoryModule = module {

  single { DataRepository(get(),get()) }

}
