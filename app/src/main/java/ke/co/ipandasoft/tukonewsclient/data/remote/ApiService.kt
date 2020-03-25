package ke.co.ipandasoft.tukonewsclient.data.remote


import ke.co.ipandasoft.tukonewsclient.data.models.NewsCategory
import ke.co.ipandasoft.tukonewsclient.data.models.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

  @GET("categories")
  fun getNewsCategoryList(): Deferred<List<NewsCategory>>

  @GET("v3/post/list")
  fun getNewsList(@Query("category") category:String,@Query("page") page:String,
                  @Query("per-page") perPage:String): Deferred<NewsResponse>
}
