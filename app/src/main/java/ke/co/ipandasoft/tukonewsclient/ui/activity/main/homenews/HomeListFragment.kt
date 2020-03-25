package ke.co.ipandasoft.tukonewsclient.ui.activity.main.homenews

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import ke.co.ipandasoft.tukonewsclient.R
import ke.co.ipandasoft.tukonewsclient.data.models.NewsCategory
import ke.co.ipandasoft.tukonewsclient.data.models.NewsResponse
import ke.co.ipandasoft.tukonewsclient.data.models.Post
import ke.co.ipandasoft.tukonewsclient.data.remote.Response
import ke.co.ipandasoft.tukonewsclient.ui.activity.main.browser.BrowserActivity
import ke.co.ipandasoft.tukonewsclient.ui.adapters.HomeNewsAdapter
import ke.co.ipandasoft.tukonewsclient.ui.adapters.OnItemClickListener
import ke.co.ipandasoft.tukonewsclient.ui.adapters.OnNewsLikedListener
import ke.co.ipandasoft.tukonewsclient.ui.base.BaseFragment
import ke.co.ipandasoft.tukonewsclient.utils.NavigationUtils
import ke.co.ipandasoft.tukonewsclient.utils.PaginationAdapterCallback
import ke.co.ipandasoft.tukonewsclient.utils.PaginationScrollListener
import kotlinx.android.synthetic.main.fragment_home_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


class HomeListFragment :BaseFragment(), PaginationAdapterCallback,OnItemClickListener,OnNewsLikedListener {

    private var newsCategory:NewsCategory?=null
    private val viewModel by viewModel<HomeListViewModel>()
    private var newsListMutable:MutableList<Post> =arrayListOf()

    private lateinit var newsAdapter: HomeNewsAdapter
    internal lateinit var linearLayoutManager: LinearLayoutManager

    private var isLoading = false
    private var isLastPage = false
    private val PAGE_START = 1
    private var currentPage = PAGE_START
    private var TOTAL_PAGES=2
    private var per_page:String="20"
    lateinit var paginationScrollListener: PaginationScrollListener

    companion object{

        fun getInstance(newsCategory: NewsCategory):HomeListFragment{
            val fragment = HomeListFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.newsCategory=newsCategory
            return fragment
        }
    }

    override fun getLayoutId(): Int {
      return R.layout.fragment_home_list
    }

    override fun initView() {
        newsAdapter= HomeNewsAdapter(this.context!!)
        newsAdapter.setOnItemClickListener(this)
        newsAdapter.setOnNewsLikedListener(this)

        linearLayoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager=linearLayoutManager
        mRecyclerView.adapter=newsAdapter
        setRecyclerViewListener()
        newsAdapter.setPaginationCallback(this)
        mRecyclerView.addOnScrollListener(paginationScrollListener)
    }


    private fun setRecyclerViewListener() {
        paginationScrollListener=object :PaginationScrollListener(linearLayoutManager){
            override fun isLastPage(): Boolean {
                return false
            }

            override fun loadMoreItems() {
                this@HomeListFragment.isLoading=true
                currentPage += 1
                initNewsLoad(currentPage)
                Timber.e("CURRENT PAGE $currentPage")

            }

            override fun getTotalPageCount(): Int {
                return TOTAL_PAGES
            }

            override fun isLoading(): Boolean {
                return this@HomeListFragment.isLoading
            }

        }
    }

    private fun initNewsLoad(page:Int) {
        if (page==1){
            viewModel.fetchNews(newsCategory!!.slug,page.toString(),per_page)
            viewModel.newsLiveData.observe(this, Observer {
                handleState(it)

            })
        }else{
            viewModel.fetchNews(newsCategory!!.slug,page.toString(),per_page)
            viewModel.newsLiveDataNewData.observe(this, Observer {
                handleState(it)

            })
        }

    }

    override fun lazyLoad() {
        initNewsLoad(currentPage)
    }

    private fun handleState(it: Response<NewsResponse>?) {
        when(it){
            is Response.Loading->{
                Timber.e("LOADING DATA$it")
                if (currentPage==1){
                    multipleStatusView.showLoading()
                }
                else{

                }

            }
            is Response.Success->{


                Timber.e("HANDLING DATA"+it.data.posts!!.size)

                if (currentPage==1){
                    newsAdapter.addAll(it.data.posts as List<Post>)
                    multipleStatusView.showContent()

                    if (currentPage <= TOTAL_PAGES)
                        newsAdapter.addLoadingFooter()
                    else
                        isLastPage = true
                }
                else{
                    newsAdapter.removeLoadingFooter()
                    newsAdapter.addAll(it.data.posts as List<Post>)

                    if (currentPage != TOTAL_PAGES) {
                        newsAdapter.addLoadingFooter()
                    }
                    else {
                        isLastPage = true
                    }

                }

           }
            is Response.Error->{
                Timber.e("ERROR RESPONSE"+ it.exception)
                multipleStatusView.showError()
            }
        }
    }



    override fun retryPageLoad() {
        //initNewsLoad(currentPage,per_page)
    }

    override fun onItemClick(obj: Any?, position: Int) {
        val post=obj as Post
        NavigationUtils.navigateWithBundle(this.activity as AppCompatActivity,true, post.url!!,
            post.title!!,BrowserActivity::class.java)

    }

    override fun onNewsLiked(obj: Any?, position: Int) {
        viewModel.saveLikedNews(obj as Post)
        viewModel.saveUpdateNewsStatus(true,obj)
        Toast.makeText(this.context,"ITEM LiKED"+Gson().toJson(obj),Toast.LENGTH_SHORT).show()
    }


}