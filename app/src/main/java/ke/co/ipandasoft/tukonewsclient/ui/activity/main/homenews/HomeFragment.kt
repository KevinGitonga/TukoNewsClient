package ke.co.ipandasoft.tukonewsclient.ui.activity.main.homenews

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.gson.Gson
import ke.co.ipandasoft.tukonewsclient.R
import ke.co.ipandasoft.tukonewsclient.data.models.NewsCategory
import ke.co.ipandasoft.tukonewsclient.ui.base.BaseFragment
import ke.co.ipandasoft.tukonewsclient.ui.base.BaseFragmentAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


class HomeFragment : BaseFragment() {

    private var newsCategoryList: MutableList<NewsCategory> = arrayListOf()
    private val viewModel by viewModel<HomeViewModel>()
    private val tabList = ArrayList<String>()
    private val fragments = ArrayList<Fragment>()

    companion object {
        fun getInstance(): HomeFragment {
            val fragment =
                HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {

    }

    override fun lazyLoad() {
        viewModel.mutableCategoryList.observe(this, Observer {
            handleRespose(it)
        })

    }

    private fun handleRespose(it: List<NewsCategory>?) {
        Timber.e("DATA CATS"+Gson().toJson(it))
        newsCategoryList.addAll(it!!)
        for (NewsCategory in newsCategoryList){
            tabList.add(NewsCategory.slug)
            fragments.add(HomeListFragment.getInstance(NewsCategory))
        }

        viewPager.adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
        tabLayout.setupWithViewPager(viewPager)
        viewPager.offscreenPageLimit=4
        viewPager.currentItem = 6
    }
}

