package ke.co.ipandasoft.tukonewsclient.ui.activity.main.bookmarks

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import ke.co.ipandasoft.tukonewsclient.R
import ke.co.ipandasoft.tukonewsclient.data.models.NewsCategory
import ke.co.ipandasoft.tukonewsclient.data.models.Post
import ke.co.ipandasoft.tukonewsclient.ui.activity.main.browser.BrowserActivity
import ke.co.ipandasoft.tukonewsclient.ui.adapters.HomeNewsAdapter
import ke.co.ipandasoft.tukonewsclient.ui.adapters.OnItemClickListener
import ke.co.ipandasoft.tukonewsclient.ui.adapters.OnNewsLikedListener
import ke.co.ipandasoft.tukonewsclient.ui.base.BaseFragment
import ke.co.ipandasoft.tukonewsclient.utils.NavigationUtils
import kotlinx.android.synthetic.main.fragment_liked_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


class BookMarksListFragment :BaseFragment(),OnItemClickListener,OnNewsLikedListener {

    private var newsCategory:NewsCategory?=null
    private val viewModel by viewModel<BookMarksListViewModel>()
    private var newsListMutable:MutableList<Post> =arrayListOf()

    private lateinit var newsAdapter: HomeNewsAdapter
    internal lateinit var linearLayoutManager: LinearLayoutManager
    private var isLoading = false
    private var isLastPage = false


    companion object{
        fun getInstance():BookMarksListFragment{
            val fragment = BookMarksListFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int {
      return R.layout.fragment_liked_list
    }

    override fun initView() {
        multipleStatusView.showLoading()
        newsAdapter= HomeNewsAdapter(this.context!!)
        newsAdapter.setOnItemClickListener(this)
        newsAdapter.setOnNewsLikedListener(this)
        linearLayoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager=linearLayoutManager
        mRecyclerView.adapter=newsAdapter

    }


    override fun lazyLoad() {
        if (isVisible){
            viewModel.fetchLikedNews()
            viewModel.likedNewsLiveData.observe(this, Observer {
                handleState(it)
            })
        }

    }

    private fun handleState(it: List<Post>?) {

        if (it!!.isNotEmpty()){
            Timber.e("LIKED NEWS FROM DB"+ Gson().toJson(it))

            newsAdapter.addAll(it)
            newsAdapter.notifyDataSetChanged()
            multipleStatusView.showContent()
        }else{
            multipleStatusView.showEmpty()
        }
    }




    override fun onItemClick(obj: Any?, position: Int) {
        val post=obj as Post
        NavigationUtils.navigateWithBundle(this.activity as AppCompatActivity,true, post.url!!,
            post.title!!, BrowserActivity::class.java)

    }

    override fun onNewsLiked(obj: Any?, position: Int) {
        Toast.makeText(this.context,"NEWS REMOVED",Toast.LENGTH_SHORT).show()
        viewModel.removeLikedPost(obj as Post)
        newsAdapter.remove(obj)
        newsAdapter.notifyItemRemoved(position)
    }

    override fun onResume() {
        super.onResume()
        if (newsListMutable.isNotEmpty()){
            newsListMutable.clear()

            viewModel.fetchLikedNews()
            viewModel.likedNewsLiveData.observe(this, Observer {
                handleState(it)
            })
        }
    }


}