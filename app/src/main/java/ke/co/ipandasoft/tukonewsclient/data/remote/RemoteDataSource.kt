package ke.co.ipandasoft.tukonewsclient.data.remote

import com.google.gson.Gson
import ke.co.ipandasoft.tukonewsclient.data.DataSource
import ke.co.ipandasoft.tukonewsclient.data.models.NewsCategory
import ke.co.ipandasoft.tukonewsclient.data.models.NewsResponse
import ke.co.ipandasoft.tukonewsclient.data.models.Post
import timber.log.Timber
import java.lang.Exception


class RemoteDataSource(private val apiService: ApiService) :DataSource {

    override fun saveNewsCategories(newsCategoryList: List<NewsCategory>) {

    }


    init {
        Timber.e("${this.javaClass.simpleName} Inject RemoteData Source")
    }

    override suspend fun getNewsCategories(): Response<List<NewsCategory>> {
        return try {
            Response.Loading
            val gson= Gson()
            val newsCategoryResponse = apiService.getNewsCategoryList().await()

            Timber.e(gson.toJson(newsCategoryResponse))

            Response.Success(newsCategoryResponse)

        }catch (ex:Exception){
            val gson= Gson()
            Timber.e(gson.toJson(ex))
            Response.Error(ex)
    }

    }

    override suspend fun getNewsList(category:String,page:String,perPage:String): Response<NewsResponse> {
        return try {
            Response.Loading
            val newsResponse=apiService.getNewsList(category,page,perPage).await()
            Timber.e("NEWS RESPONSE"+Gson().toJson(newsResponse))
            Response.Success(newsResponse)
        }catch (ex:Exception){
            Timber.e("ERROR NEWS FETCH"+Gson().toJson(ex))
            Response.Error(ex)

        }
    }

    override fun getLocalNewsCategory(): List<NewsCategory> {
        TODO("Not yet implemented")
    }

    override fun getLikedNewsListLocal(): List<Post> {
        TODO("Not yet implemented")
    }

    override fun saveLikedNews(newsPost: Post) {
        TODO("Not yet implemented")
    }

    override fun clearRoomDatabase() {
        TODO("Not yet implemented")
    }


}