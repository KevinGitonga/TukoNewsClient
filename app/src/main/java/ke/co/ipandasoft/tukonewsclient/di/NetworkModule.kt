package ke.co.ipandasoft.tukonewsclient.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import ke.co.ipandasoft.tukonewsclient.constants.Constants
import ke.co.ipandasoft.tukonewsclient.data.remote.ApiService
import ke.co.ipandasoft.tukonewsclient.data.remote.RemoteDataSource
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {


  single {
    Retrofit.Builder()
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
  }

    single<ApiService> {
         get<Retrofit.Builder>()
        .baseUrl(Constants.API_BASE_URL)
        .build()
        .create(ApiService::class.java)
    }

  single { RemoteDataSource(get()) }

}
