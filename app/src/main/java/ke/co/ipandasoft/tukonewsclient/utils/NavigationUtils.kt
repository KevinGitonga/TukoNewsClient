package ke.co.ipandasoft.tukonewsclient.utils

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import ke.co.ipandasoft.tukonewsclient.constants.Constants
import ke.co.ipandasoft.tukonewsclient.data.models.Post

class NavigationUtils{

    companion object{

        fun navigate(activity: AppCompatActivity,javaClass: Class<out Activity>){
            val navigationIntent=Intent(activity,javaClass)
            activity.startActivity(navigationIntent)
        }


        fun navigateWithBundle(activity: AppCompatActivity,boolean: Boolean,newsUrl:String,newsTitle:String,javaClass: Class<out Activity>){
            val navigationIntent=Intent(activity,javaClass)
            navigationIntent.putExtra("isArticle",boolean)
            navigationIntent.putExtra("news_url",newsUrl)
            navigationIntent.putExtra("newsTitle",newsTitle)
            activity.startActivity(navigationIntent)
        }

        fun navigateHome(activity: AppCompatActivity,post:Post,javaClass: Class<out Activity>){
            val navigationIntent=Intent(activity,javaClass)
            navigationIntent.putExtra(Constants.SPLASH_POST_DATA_INTENT,Gson().toJson(post))
            activity.startActivity(navigationIntent)
        }

    }

}