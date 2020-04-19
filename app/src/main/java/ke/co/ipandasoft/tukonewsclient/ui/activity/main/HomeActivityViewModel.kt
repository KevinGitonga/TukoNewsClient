package ke.co.ipandasoft.tukonewsclient.ui.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ke.co.ipandasoft.tukonewsclient.data.models.NewsResponse
import ke.co.ipandasoft.tukonewsclient.data.remote.Response
import ke.co.ipandasoft.tukonewsclient.data.repository.DataRepository
import kotlinx.coroutines.launch

class HomeActivityViewModel(private val dataRepository: DataRepository) :ViewModel(){

    private val newsListMutable= MutableLiveData<Response<NewsResponse>>()
    val newsLiveData: LiveData<Response<NewsResponse>>
        get() = newsListMutable


    fun fetchNews(category: String,page:String,perPage:String){

        viewModelScope.launch{
            val newNotification=dataRepository.getNewsList("latest","1","1")
        }
    }


}