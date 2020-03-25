package ke.co.ipandasoft.tukonewsclient.data

import ke.co.ipandasoft.tukonewsclient.data.models.NewsCategory
import ke.co.ipandasoft.tukonewsclient.data.models.NewsResponse
import ke.co.ipandasoft.tukonewsclient.data.models.Post
import ke.co.ipandasoft.tukonewsclient.data.remote.Response


interface DataSource {

    suspend fun getNewsCategories():Response<List<NewsCategory>>

    fun saveNewsCategories(newsCategoryList: List<NewsCategory>)

    suspend fun getNewsList(category:String,page:String,perPage:String):Response<NewsResponse>

    fun getLocalNewsCategory():List<NewsCategory>

    fun getLikedNewsListLocal():List<Post>

    fun saveLikedNews(newsPost:Post)

    fun clearRoomDatabase()

}