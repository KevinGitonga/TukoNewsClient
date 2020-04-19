package ke.co.ipandasoft.tukonewsclient.data.local.room

import androidx.room.*
import ke.co.ipandasoft.tukonewsclient.data.models.Post

@Dao
interface NewsPostDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertLikedNews(newsPost: Post)

  @Query("UPDATE news_post_table SET isLiked = :boolean WHERE post_id = :newsPostId")
  fun newsLiked(boolean: Boolean,newsPostId: Int)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertNewsNotification(newsPost: Post)

  @Query("UPDATE news_post_table SET isNotification = :boolean WHERE post_id = :newsPostId")
  fun isNotification(boolean: Boolean,newsPostId: Int)

  @Query("SELECT * FROM news_post_table WHERE post_id = :id_")
  fun getNewsPost(id_: Int): Post

  @Query("SELECT * FROM news_post_table ORDER BY publishDate ASC")
  fun getLikedNewsList(): List<Post>

  @Delete
  fun removeLiked(newsPost: Post)

  @Query("DELETE FROM news_post_table")
  fun deleteAllData()

}
