package ke.co.ipandasoft.tukonewsclient.data.local

import com.google.gson.Gson
import ke.co.ipandasoft.tukonewsclient.data.local.room.NewsCatDao
import ke.co.ipandasoft.tukonewsclient.data.local.room.NewsPostDao
import ke.co.ipandasoft.tukonewsclient.data.models.NewsCategory
import ke.co.ipandasoft.tukonewsclient.data.models.Post
import timber.log.Timber

class LocalDataSource(private val newsCatDao: NewsCatDao,
                      private val newsPostDao: NewsPostDao){

     fun getNewsCategory(): List<NewsCategory> {
       return newsCatDao.getNewsCatList()
    }

      fun saveNewsCategory(news: List<NewsCategory>) {
          Timber.e("data for dao"+Gson().toJson(news))
        newsCatDao.insertnewsCatList(news)
   }

    fun getLikedNewsList():List<Post>{
        return newsPostDao.getLikedNewsList()
    }

    fun saveLikedNews(post: Post){
        newsPostDao.insertLikedNews(post)
    }

    fun setLikedNews(boolean: Boolean,post: Post){
        newsPostDao.newsLiked(boolean, post.id!!)
    }

    fun removeNewsPost(post: Post){
        newsPostDao.removeLiked(post)
    }

    fun clearRoomDatabase() {
        newsCatDao.deleteAllData()
        newsPostDao.deleteAllData()
    }

}