package ke.co.ipandasoft.tukonewsclient.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_category_table")
data class NewsCategory(
    @PrimaryKey val id: Long,
    val name: String,
    val slug: String

)