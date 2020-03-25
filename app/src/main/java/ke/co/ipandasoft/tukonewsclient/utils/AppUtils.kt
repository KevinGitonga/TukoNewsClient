package ke.co.ipandasoft.tukonewsclient.utils

import android.R
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import org.ocpsoft.prettytime.PrettyTime
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class AppUtils private constructor() {


    init {
        throw Error("Do not need instantiate!")
    }

    companion object {

        private val DEBUG = true
        private val TAG = "AppUtils"
        private const val TIME_FORMAT_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ssZ"


        fun clearCache(context: Context): Boolean {
            var result = context.cacheDir.deleteRecursively()
            Timber.e("DELETE CACHE  $result")
            return result
        }


        fun getVerCode(context: Context): Int {
            var verCode = -1
            try {
                val packageName = context.packageName
                verCode = context.packageManager
                        .getPackageInfo(packageName, 0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return verCode
        }



        val maxMemory: Long
            get() = Runtime.getRuntime().maxMemory() / 1024


        fun getVerName(context: Context): String {
            var verName = ""
            try {
                val packageName = context.packageName
                verName = context.packageManager
                        .getPackageInfo(packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return verName
        }



        fun getRelativeDateTimeString(date: Date):CharSequence {
            var locale = Locale.getDefault()
            if (locale.language == "kk" && Build.VERSION.SDK_INT >= 21)
            {
                locale = Locale.forLanguageTag("ru")
            }
            val prettyTime = PrettyTime(locale)
            return prettyTime.format(date)
        }

        fun getDateIso(str:String):Date {
            if (TextUtils.isEmpty(str))
            {
                return Date()
            }
            try
            {
                return SimpleDateFormat(TIME_FORMAT_WITH_TIMEZONE, Locale.ENGLISH).parse(str)
            }
            catch (e: ParseException) {
                e.printStackTrace()
                return Date()
            }
        }
    }


}