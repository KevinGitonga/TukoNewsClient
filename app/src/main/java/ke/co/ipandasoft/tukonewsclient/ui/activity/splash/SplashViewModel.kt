package ke.co.ipandasoft.tukonewsclient.ui.activity.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import ke.co.ipandasoft.tukonewsclient.data.models.NewsCategory
import ke.co.ipandasoft.tukonewsclient.data.remote.Response
import ke.co.ipandasoft.tukonewsclient.data.repository.DataRepository
import kotlinx.coroutines.launch
import timber.log.Timber


class SplashViewModel (
    private val dataRepository: DataRepository
) : ViewModel() {


    private val newsCategoryList=MutableLiveData<Response<List<NewsCategory>>>()
    val newsCategoryListLive: LiveData<Response<List<NewsCategory>>>
        get() = newsCategoryList


    private val newsCategoryListLocal=MutableLiveData<List<NewsCategory>>()
    val newsCategoryListLiveLocal: LiveData<List<NewsCategory>>
        get() = newsCategoryListLocal



    init {
        Timber.e(this.javaClass.simpleName+"injection MainViewModel")
    }

     fun persistCategories(newsCategory: List<NewsCategory>){
        Timber.e("DATA FOR DB" +Gson().toJson(newsCategory))
        dataRepository.saveNewsCategories(newsCategory)
    }

     fun getLocalCategories(){
         val localCategories=dataRepository.getLocalNewsCategory()
         Timber.e("LOCAL DATA CATS"+Gson().toJson(localCategories))
         newsCategoryListLocal.postValue(localCategories)
    }

    fun getRemoteDataCategories(){
        viewModelScope.launch{
            newsCategoryList.postValue(Response.Loading)
            val newsFromRepo:Response<List<NewsCategory>> =dataRepository.getNewsCategories()
            newsCategoryList.postValue(newsFromRepo)

        }
    }


}
