package ke.co.ipandasoft.tukonewsclient.ui.activity.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import ke.co.ipandasoft.tukonewsclient.R
import ke.co.ipandasoft.tukonewsclient.TukoNewsApp.Companion.context
import ke.co.ipandasoft.tukonewsclient.constants.Constants
import ke.co.ipandasoft.tukonewsclient.data.models.Post
import ke.co.ipandasoft.tukonewsclient.data.repository.DataRepository
import ke.co.ipandasoft.tukonewsclient.ui.activity.splash.SplashActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import java.io.IOException
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL

class NotificationRequestWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context,
    workerParams
) ,KoinComponent{

    private val WORK_RESULT = "work_result"
    private val dataRepository : DataRepository by inject()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO)  {
        try {
            val outPutData=dataRepository.getNewsList("latest","1","1")
             val stringDataOuptput:JsonElement= Gson().toJsonTree(outPutData)
             val dataElement: JsonObject = stringDataOuptput.asJsonObject.getAsJsonObject("data")
             val postsArray:JsonArray=dataElement.getAsJsonArray("posts")
             val listType: Type =object : TypeToken<List<Post>>(){}.type
             val postList:List<Post> = Gson().fromJson(postsArray,listType)
             Timber.e("POSTS DATA"+ Gson().toJson(postList))
             showNotification(postList)
             Result.success()
        }

         catch (e: Exception) {
            Timber.e(e.localizedMessage + "Failure in doing work")
            Result.failure()
        }


    }

    private fun showNotification(postList: List<Post>){
        val channelId = "task_channel"
        val channelName = "task_name"

        val post=postList[0]

        // Do the work here
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(channel)
        }

        val bitmap = getBitmapFromURL(post.img!!)

        val notificationStyle = NotificationCompat.BigPictureStyle()
        notificationStyle.setBigContentTitle(post.title)
        notificationStyle.setSummaryText(post.description)
        notificationStyle.bigPicture(bitmap)

        val intent=Intent(context,SplashActivity::class.java)
        intent.putExtra(Constants.SPLASH_POST_DATA_INTENT,Gson().toJson(post))

        val resultPendingIntent = PendingIntent.getActivity(
                context,
        0,
        intent,
        PendingIntent.FLAG_CANCEL_CURRENT)

        val notification:Notification = NotificationCompat.Builder(context,channelId).apply {
            setContentTitle(post.title)
            setContentText(post.description)
            setStyle(notificationStyle)
            setContentIntent(resultPendingIntent)
            setSmallIcon(R.drawable.ic_collection)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }.build()

        nm.notify(System.currentTimeMillis().toInt(), notification)
    }

    fun getBitmapFromURL(strURL: String): Bitmap? {
        try {
            val url = URL(strURL)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }
}