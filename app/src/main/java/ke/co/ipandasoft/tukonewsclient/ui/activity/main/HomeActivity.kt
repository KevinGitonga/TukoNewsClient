package ke.co.ipandasoft.tukonewsclient.ui.activity.main

import androidx.fragment.app.FragmentTransaction
import ke.co.ipandasoft.tukonewsclient.R
import ke.co.ipandasoft.tukonewsclient.ui.activity.main.bookmarks.BookMarksFragment
import ke.co.ipandasoft.tukonewsclient.ui.activity.main.bookmarks.BookMarksListFragment
import ke.co.ipandasoft.tukonewsclient.ui.activity.main.homenews.HomeFragment
import ke.co.ipandasoft.tukonewsclient.ui.activity.main.settings.SettingsFragment
import ke.co.ipandasoft.tukonewsclient.ui.base.BaseActivity
import kotlinx.android.synthetic.main.home_activity.*
import org.jetbrains.anko.longToast

class HomeActivity :BaseActivity(){

    private var homeFragment: HomeFragment? = null
    private var bookMarksFragment: BookMarksFragment? = null
    private var settingsFragment: SettingsFragment? = null


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