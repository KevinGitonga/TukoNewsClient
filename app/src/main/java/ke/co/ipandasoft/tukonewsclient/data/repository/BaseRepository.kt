package ke.co.ipandasoft.tukonewsclient.data.repository

import ke.co.ipandasoft.tukonewsclient.data.models.NewsCategory
import ke.co.ipandasoft.tukonewsclient.data.remote.Response

interface BaseRepository {

    suspend fun getNewsCategories(): Response<List<NewsCategory>>

    fun saveNewsCategory(news:List<NewsCategory>)

    fun getNewsCategory():List<NewsCategory>
    
    suspend fun getNewsList():Response<List<NewsCategory>>
    
    fun getNewsListLocal():List<NewsCategory>
    
    fun saveNewsList():List<NewsCategory>
}