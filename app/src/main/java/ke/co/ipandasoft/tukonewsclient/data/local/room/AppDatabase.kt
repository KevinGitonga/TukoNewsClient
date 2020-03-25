package ke.co.ipandasoft.tukonewsclient.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ke.co.ipandasoft.tukonewsclient.data.models.NewsCategory
import ke.co.ipandasoft.tukonewsclient.data.models.Post


@Database(entities = [NewsCategory::class,Post::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

  abstract fun newsCatDao(): NewsCatDao

  abstract fun newsPostDao():NewsPostDao
  
}
