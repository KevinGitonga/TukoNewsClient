package ke.co.ipandasoft.tukonewsclient.ui.activity.main.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ke.co.ipandasoft.tukonewsclient.data.models.NewsResponse
import ke.co.ipandasoft.tukonewsclient.data.models.Post
import ke.co.ipandasoft.tukonewsclient.data.remote.Response
import ke.co.ipandasoft.tukonewsclient.data.repository.DataRepository
import kotlinx.coroutines.launch

class BookMarksListViewModel(private val dataRepository: DataRepository):ViewModel(){

     private val likedNewsListMutable=MutableLiveData<List<Post>>()
      val likedNewsLiveData:LiveData<List<Post>>
       get() = likedNewsListMutable



    fun fetchLikedNews(){
        val likedNews=dataRepository.getLikedNewsListLocal()
        likedNewsListMutable.postValue(likedNews)
    }

    fun removeLikedPost(post: Post){
        dataRepository.removeLikedPost(post)
    }

}