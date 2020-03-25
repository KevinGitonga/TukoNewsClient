package ke.co.ipandasoft.tukonewsclient.ui.activity.main.settings


import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ke.co.ipandasoft.tukonewsclient.R
import ke.co.ipandasoft.tukonewsclient.ui.activity.splash.SplashActivity
import ke.co.ipandasoft.tukonewsclient.ui.base.BaseFragment
import ke.co.ipandasoft.tukonewsclient.utils.AppUtils
import ke.co.ipandasoft.tukonewsclient.utils.NavigationUtils
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : BaseFragment() {

    private val viewModel by viewModel<ManageCategoriesViewModel>()

    companion object {
        fun getInstance(): SettingsFragment {
            val fragment =
                SettingsFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun getLayoutId(): Int {
        return  R.layout.fragment_settings
    }

    override fun initView() {
        toolbar_title.text = "SETTINGS"
    }

    override fun lazyLoad() {
        bindDataToUi()
    }

    private fun bindDataToUi() {
        tv_about.setOnClickListener {
           NavigationUtils.navigate(this.context as AppCompatActivity,AboutAppActivity::class.java)
        }
        tv_clear.setOnClickListener {
            AlertDialog.Builder(activity!!)
                .setMessage("ARE YOU SURE YOU WANT TO CLEAR")
                .setNegativeButton("NO") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("YES") { dialog, _ ->

                    Toast.makeText(this.context,"APP CACHE CLEARED",Toast.LENGTH_SHORT)
                    if (AppUtils.clearCache(this.context!!)){
                       viewModel.clearRoomDatabase()
                        NavigationUtils.navigate(this.context as AppCompatActivity,SplashActivity::class.java)
                    }
                }
                .create()
                .show()
        }

        tv_setting.setOnClickListener {
          Toast.makeText(this.context,"Coming soon...",Toast.LENGTH_SHORT)
        }

        tv_manage_categories.setOnClickListener {
           NavigationUtils.navigate(this.context as AppCompatActivity,ManageCategoriesActivity::class.java)
        }
    }


}
