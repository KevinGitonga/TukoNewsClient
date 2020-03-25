package ke.co.ipandasoft.tukonewsclient.di

import ke.co.ipandasoft.tukonewsclient.ui.activity.main.bookmarks.BookMarksListViewModel
import ke.co.ipandasoft.tukonewsclient.ui.activity.main.homenews.HomeListViewModel
import ke.co.ipandasoft.tukonewsclient.ui.activity.splash.SplashViewModel
import ke.co.ipandasoft.tukonewsclient.ui.activity.main.homenews.HomeViewModel
import ke.co.ipandasoft.tukonewsclient.ui.activity.main.settings.ManageCategoriesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

  viewModel {
      SplashViewModel(
          get()
      )
  }


  viewModel {
      HomeViewModel(get())
  }

    viewModel {
        HomeListViewModel(get())
    }

    viewModel {
        BookMarksListViewModel(get())
    }

    viewModel {
        ManageCategoriesViewModel(get())
    }
}
