package ke.co.ipandasoft.tukonewsclient.ui.activity.main.settings

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


class ManageCategoriesViewModel (
    private val dataRepository: DataRepository
) : ViewModel() {

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

    fun clearRoomDatabase(){
        dataRepository.clearRoomDatabase()
    }


}
