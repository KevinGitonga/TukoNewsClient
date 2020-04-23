package ke.co.ipandasoft.tukonewsclient.ui.activity.main

import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.work.*
import com.google.gson.Gson
import ke.co.ipandasoft.tukonewsclient.R
import ke.co.ipandasoft.tukonewsclient.constants.Constants
import ke.co.ipandasoft.tukonewsclient.data.models.Post
import ke.co.ipandasoft.tukonewsclient.ui.activity.main.bookmarks.BookMarksFragment
import ke.co.ipandasoft.tukonewsclient.ui.activity.main.bookmarks.BookMarksListFragment
import ke.co.ipandasoft.tukonewsclient.ui.activity.main.browser.BrowserActivity
import ke.co.ipandasoft.tukonewsclient.ui.activity.main.homenews.HomeFragment
import ke.co.ipandasoft.tukonewsclient.ui.activity.main.settings.SettingsFragment
import ke.co.ipandasoft.tukonewsclient.ui.activity.notifications.NotificationRequestWorker
import ke.co.ipandasoft.tukonewsclient.ui.base.BaseActivity
import ke.co.ipandasoft.tukonewsclient.utils.NavigationUtils
import kotlinx.android.synthetic.main.home_activity.*
import org.jetbrains.anko.longToast
import timber.log.Timber
import java.util.concurrent.TimeUnit

class HomeActivity :BaseActivity(){

    private var homeFragment: HomeFragment? = null
    private var bookMarksFragment: BookMarksFragment? = null
    private var settingsFragment: SettingsFragment? = null

    private var post: Post?=null


    override fun layoutId(): Int {
        return R.layout.home_activity
    }

    override fun initData() {
    }

    override fun initView() {
        setupViews()
    }

    private fun setupViews() {

    }

    override fun start() {
        initBottomNav()
        switchFragment(0)
        setupWorker()
        openNewsIntent()
    }

    private fun openNewsIntent() {
        val postData=intent.getStringExtra(Constants.SPLASH_POST_DATA_INTENT)
        if (!postData.isNullOrEmpty()){
            post=Gson().fromJson(postData,Post::class.java)
            NavigationUtils.navigateWithBundle(this,true, post!!.url!!, post!!.title!!,
                BrowserActivity::class.java)
        }

    }

    private fun setupWorker() {
        /*Setting up different constraints on the work request.
        */
        val constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.CONNECTED)
            setRequiresCharging(false)
            setRequiresStorageNotLow(false)
            setRequiresBatteryNotLow(true)                 // if the battery is not low
        }.build()

        val workManager= WorkManager.getInstance()
        val renderNotification= PeriodicWorkRequest.Builder(
            NotificationRequestWorker::class.java,
            30, TimeUnit.MINUTES)
            .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
            .setConstraints(constraints).build()
        workManager.enqueueUniquePeriodicWork("BACKGROUND NOTIFICATIONS", ExistingPeriodicWorkPolicy.KEEP,renderNotification)

        Timber.e("WORK ENQUEUED")
        workManager.getWorkInfoByIdLiveData(renderNotification.id).observe(this, Observer {

            if (it != null && it.state == WorkInfo.State.SUCCEEDED) {
                Timber.e("SUCCESS PERF JOB"+ Gson().toJson(it))
            }
        })
    }


    private fun initBottomNav() {
        bottomNavView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.homeFragment->switchFragment(0)
                R.id.bookmarksFragment->switchFragment(1)
                R.id.settingsFragment->switchFragment(2)

                else -> {
                    false

                }

            }

            true


        }

    }


    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0
            -> homeFragment?.let {
                transaction.show(it)
            } ?: HomeFragment.getInstance().let {
                homeFragment = it
                transaction.add(R.id.mainContainer, it, "home")
            }
            1
            -> bookMarksFragment?.let {
                transaction.show(it)
            } ?: BookMarksFragment.getInstance().let {
                bookMarksFragment = it
                transaction.add(R.id.mainContainer, it, "bookmarks") }
            2
            -> settingsFragment?.let {
                transaction.show(it)
            } ?: SettingsFragment.getInstance().let {
                settingsFragment = it
                transaction.add(R.id.mainContainer, it, "settings") }
            else -> {

            }
        }

        transaction.commitAllowingStateLoss()
    }

    private fun hideFragments(transaction: FragmentTransaction) {
        homeFragment?.let { transaction.hide(it) }
        bookMarksFragment?.let { transaction.hide(it) }
        settingsFragment?.let { transaction.hide(it) }
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

}