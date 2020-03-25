package ke.co.ipandasoft.tukonewsclient.data.local.room

import androidx.room.*
import ke.co.ipandasoft.tukonewsclient.data.models.NewsCategory
import ke.co.ipandasoft.tukonewsclient.data.models.Post

@Dao
interface NewsCatDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertnewsCatList(newsCategory: List<NewsCategory>)

  @Query("SELECT * FROM news_category_table WHERE id = :id_")
  fun getNewsCat(id_: Long): NewsCategory

  @Query("SELECT * FROM news_category_table ORDER BY name ASC")
  fun getNewsCatList(): List<NewsCategory>

  @Query("DELETE FROM news_category_table")
  fun deleteAllData()


}
