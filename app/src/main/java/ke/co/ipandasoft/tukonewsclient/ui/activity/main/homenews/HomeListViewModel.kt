package ke.co.ipandasoft.tukonewsclient.ui.activity.main.homenews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ke.co.ipandasoft.tukonewsclient.data.models.NewsResponse
import ke.co.ipandasoft.tukonewsclient.data.models.Post
import ke.co.ipandasoft.tukonewsclient.data.remote.Response
import ke.co.ipandasoft.tukonewsclient.data.repository.DataRepository
import kotlinx.coroutines.launch

class HomeListViewModel(private val dataRepository: DataRepository):ViewModel(){

     private val newsListMutable=MutableLiveData<Response<NewsResponse>>()
      val newsLiveData:LiveData<Response<NewsResponse>>
       get() = newsListMutable

    private val newsListMutableNewData=MutableLiveData<Response<NewsResponse>>()
    val newsLiveDataNewData:LiveData<Response<NewsResponse>>
        get() = newsListMutableNewData



    fun fetchNews(category: String,page:String,perPage:String){

        viewModelScope.launch{
            if (page.toInt()==1){
                newsListMutable.postValue(Response.Loading)
                val newsList=dataRepository.getNewsList(category,page,perPage)
                newsListMutable.postValue(newsList)
            }else{
                newsListMutableNewData.postValue(Response.Loading)
                val newsListNew=dataRepository.getNewsList(category,page,perPage)
                newsListMutableNewData.postValue(newsListNew)
            }

        }
    }

    fun saveLikedNews(post: Post){
        dataRepository.saveLikedNews(post)
    }

    fun saveUpdateNewsStatus(boolean: Boolean,post: Post){
        dataRepository.updateNewsLikeStatus(boolean,post)
    }

}