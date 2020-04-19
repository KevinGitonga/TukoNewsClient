package ke.co.ipandasoft.tukonewsclient.data.models


import android.app.Notification
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ke.co.ipandasoft.tukonewsclient.di.networkModule

@Entity(tableName = "news_post_table")
data class Post(
    @SerializedName("author")
    @ColumnInfo(name = "author")
    var author: String?="",
    @SerializedName("commentsCount")
    @ColumnInfo(name = "commentsCount")
    var commentsCount: Int?=0,
    @SerializedName("content")
    @Ignore var content: List<Content?>? =null,
    @SerializedName("description")
    @ColumnInfo(name = "descrition")
    var description: String?="",
    @SerializedName("disableComments")
    @ColumnInfo(name = "disableComments")
    var disableComments: Boolean?=false,
    @SerializedName("disableReactions")
    @ColumnInfo(name = "disableReactions")
    var disableReactions: Boolean?=false,
    @SerializedName("firstCategory")
    @ColumnInfo(name = "firstCategory")
    var firstCategory: String?="",
    @SerializedName("galleryContent")
    @Ignore var galleryContent: List<Any?>?=null,
    @SerializedName("id")
    @ColumnInfo(name = "post_id")
    @PrimaryKey var id: Int?=0,
    @SerializedName("img")
    @ColumnInfo(name = "largeImg")
    var img: String?="",
    @SerializedName("imgSmall")
    @ColumnInfo(name = "imgSmall")
    var imgSmall: String?="",
    @SerializedName("isTrending")
    @ColumnInfo(name = "isTrnding")
    var isTrending: Boolean?=false,
    @SerializedName("modifyDate")
    @ColumnInfo(name = "modifyDate")
    var modifyDate: String?="",
    @SerializedName("postType")
    @ColumnInfo(name = "postType")
    var postType: Int?=0,
    @SerializedName("publishDate")
    @ColumnInfo(name = "publishDate")
    var publishDate: String?="",
    @SerializedName("reaction")
    @ColumnInfo(name = "reaction")
    var reaction: Int?=0,
    @SerializedName("reactions")
     @Ignore var reactions: List<Reaction?>?=null,
    @SerializedName("title")
    @ColumnInfo(name = "news_title")
    var title: String?="",
    @SerializedName("url")
    @ColumnInfo(name = "url")
    var url: String?="",
    @SerializedName("videoContent")
    @Ignore var videoContent: List<Any?>?=null,
    @SerializedName("views")
    @ColumnInfo(name = "views")
    var views: Int?=0,
    @SerializedName("isLiked")
    @ColumnInfo(name = "isLiked")
    var isLiked: Boolean?=false,
    @SerializedName("isNotification")
    @ColumnInfo(name = "isNotification")
    var isNotification: Boolean?=false
)