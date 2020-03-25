package ke.co.ipandasoft.tukonewsclient.ui.activity.main.homenews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ke.co.ipandasoft.tukonewsclient.data.models.NewsCategory
import ke.co.ipandasoft.tukonewsclient.data.repository.DataRepository

class HomeViewModel(private val categoryRepository: DataRepository):ViewModel(){

    private val newsCategoriesLiveData=MutableLiveData<List<NewsCategory>>()

    val mutableCategoryList:LiveData<List<NewsCategory>>
      get() = newsCategoriesLiveData


    init {
        val newsCategoryList=categoryRepository.getLocalNewsCategory()
        newsCategoriesLiveData.postValue(newsCategoryList)
    }

}