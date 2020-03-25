package ke.co.ipandasoft.tukonewsclient.ui.adapters

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ke.co.ipandasoft.tukonewsclient.R
import ke.co.ipandasoft.tukonewsclient.data.models.Post
import ke.co.ipandasoft.tukonewsclient.utils.AppUtils
import ke.co.ipandasoft.tukonewsclient.utils.PaginationAdapterCallback
import kotlinx.android.synthetic.main.item_news_minima.view.*
import kotlinx.android.synthetic.main.layout_loading_bottom.view.*

class HomeNewsAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var postList: MutableList<Post?> = ArrayList<Post?>()
    private var isLoadingAdded = false
    private var mCallback: PaginationAdapterCallback?=null
    private var onItemClickListener:OnItemClickListener?=null
    private var onNewsLikedListener:OnNewsLikedListener?=null
    private val TAG = "HomeNewsAdapter"

    companion object {

        // View Types
        private val ITEM = 0
        private val LOADING = 1

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(p0.context)

        when (p1) {
            ITEM -> {
                val viewItem = inflater.inflate(R.layout.item_news_minima, p0, false)
                viewHolder = NewsViewHolder(viewItem)
            }
            LOADING -> {
                val viewLoading = inflater.inflate(R.layout.layout_loading_bottom, p0, false)
                viewHolder = LoadingViewHolder(viewLoading)
            }
        }
        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return if (postList == null) 0 else postList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == postList!!.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {

        when (getItemViewType(p1)) {

            ITEM -> {
                val newsViewHolder = p0 as NewsViewHolder
                val post= postList!!.get(p1)

                if (post!!.isLiked!!){
                        newsViewHolder.newLikeImage.setImageDrawable(context.getDrawable(R.drawable.ic_collected))

                    }else{
                    newsViewHolder.newLikeImage.setImageDrawable(context.getDrawable(R.drawable.ic_collect))
                }
                newsViewHolder.newsTitle.text = post?.title
                newsViewHolder.newsViews.text = post?.views.toString()
                newsViewHolder.newsDate.text =
                    AppUtils.getRelativeDateTimeString(AppUtils.getDateIso(post!!.publishDate!!))
                val poster_path = post?.img
                Glide.with(context).load(poster_path).into(newsViewHolder.newsImage)
                newsViewHolder.newsItemRv.setOnClickListener { onItemClickListener!!.onItemClick(post,p1) }
                newsViewHolder.newLikeImage.setOnClickListener {
                    newsViewHolder.newLikeImage.setImageDrawable(context.getDrawable(R.drawable.ic_collected))
                    onNewsLikedListener!!.onNewsLiked(post,p1) }
            }

            LOADING -> {
                val loadingVH = p0 as LoadingViewHolder
                loadingVH.loadingTextView.visibility=View.VISIBLE
                loadingVH.mProgressBar.visibility=View.VISIBLE
            }
        }
    }


    /*
    Helpers - Pagination
    _________________________________________________________________________________________________
    */

    fun setPaginationCallback(paginationAdapterCallback: PaginationAdapterCallback) {
        this.mCallback = paginationAdapterCallback
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnNewsLikedListener(onNewsLikedListener: OnNewsLikedListener) {
        this.onNewsLikedListener = onNewsLikedListener
    }

    fun add(r: Post?) {
        postList!!.add(r)
        notifyItemInserted(postList!!.size - 1)
        Log.e(TAG, postList!!.size.toString() )
    }

    fun addAll(moveResults: List<Post>) {
        postList.addAll(moveResults)
        Log.e(TAG, postList!!.size.toString() )
    }

    fun remove(r: Post?) {
        val position = postList!!.indexOf(r)
        Log.e(TAG, "remove= $position" )
        if (position > -1) {
            postList!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }


    fun addLoadingFooter() {
            isLoadingAdded = true
            Log.e(TAG, "$isLoadingAdded" )
            Log.e(TAG, "$isLoadingAdded" )
            add(Post())
    }



    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = postList!!.size - 1
        val result = getItem(position)
        Log.e("FOOTER", "removeLoadingFooter= $position" )
        Log.e("FOOTER DATA", "$result" )

        if (result != null) {
            postList!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position: Int): Post? {
        return postList!![position]
    }


    /*
   _________________________________________________________________________________________________
    */

    internal class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val newsTitle = itemView.tv_title
        val newsImage = itemView.iv
        val newsDate = itemView.tv_date
        val newsViews = itemView.tv_views
        val newsItemRv=itemView.news_relative
        val newLikeImage=itemView.iv_bookmark


    }

    internal class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mProgressBar = itemView.loadmore_progress
        val loadingTextView=itemView.loadingTextView

    }

}