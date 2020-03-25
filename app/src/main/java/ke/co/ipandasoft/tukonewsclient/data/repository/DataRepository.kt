package ke.co.ipandasoft.tukonewsclient.data.repository

import com.google.gson.Gson
import ke.co.ipandasoft.tukonewsclient.data.DataSource
import ke.co.ipandasoft.tukonewsclient.data.local.LocalDataSource
import ke.co.ipandasoft.tukonewsclient.data.models.NewsCategory
import ke.co.ipandasoft.tukonewsclient.data.models.NewsResponse
import ke.co.ipandasoft.tukonewsclient.data.models.Post
import ke.co.ipandasoft.tukonewsclient.data.remote.RemoteDataSource
import ke.co.ipandasoft.tukonewsclient.data.remote.Response
import timber.log.Timber


class DataRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource

) :DataSource {



    init {
        Timber.e(this.javaClass.simpleName+"Inject Repository")
    }

    override suspend fun getNewsCategories(): Response<List<NewsCategory>> {
        return remoteDataSource.getNewsCategories()

    }

    override  fun saveNewsCategories(newsCategoryList: List<NewsCategory>) {
        localDataSource.saveNewsCategory(newsCategoryList)
    }


    override fun getLocalNewsCategory():List<NewsCategory> {
        if (localDataSource.getNewsCategory().isNotEmpty()){
            Timber.e("CATS DATA FROM DATABASE"+Gson().toJson(localDataSource.getNewsCategory()))
            return localDataSource.getNewsCategory()
        }else{
            return emptyList()
        }
    }

    override suspend fun getNewsList(category:String,page:String,perPage:String): Response<NewsResponse> {
        return remoteDataSource.getNewsList(category,page,perPage)
    }

    override fun getLikedNewsListLocal(): List<Post> {
        return localDataSource.getLikedNewsList()
    }

    override fun saveLikedNews(newsPost: Post) {
        localDataSource.saveLikedNews(newsPost)
    }

    override fun clearRoomDatabase() {
        localDataSource.clearRoomDatabase()
    }

    fun updateNewsLikeStatus(boolean: Boolean,newsPost: Post){
        localDataSource.setLikedNews(boolean,newsPost)
    }

    fun removeLikedPost(newsPost: Post){
        localDataSource.removeNewsPost(newsPost)
    }


}

