package ke.co.ipandasoft.tukonewsclient.ui.activity.main.settings

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import ke.co.ipandasoft.tukonewsclient.R
import ke.co.ipandasoft.tukonewsclient.data.models.NewsCategory
import ke.co.ipandasoft.tukonewsclient.ui.adapters.OnItemClickListener
import ke.co.ipandasoft.tukonewsclient.ui.adapters.TypeSortAdapter
import ke.co.ipandasoft.tukonewsclient.ui.base.BaseActivity
import ke.co.ipandasoft.tukonewsclient.utils.DragCallback
import ke.co.ipandasoft.tukonewsclient.utils.ItemTouchHelperAdapter
import ke.co.ipandasoft.tukonewsclient.utils.TypeDragHelper
import ke.co.ipandasoft.tukonewsclient.utils.setStatusTransAndDarkIcon
import kotlinx.android.synthetic.main.activity_manage_categories.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


class ManageCategoriesActivity : BaseActivity(),ItemTouchHelperAdapter {
    private var adapter:TypeSortAdapter?=null
    private var originalNewsCategoryList: ArrayList<NewsCategory> = arrayListOf()
    private val viewModel by viewModel<ManageCategoriesViewModel>()
    private var dataHasChanged:Boolean?=null

    companion object {
        private const val typeCount = 8
        private const val KEY_COUNT = "type_count"
        private const val KEY_ITEM = "item_"
        private const val KEY_SORT = "type_sort"
    }


    override fun layoutId(): Int {
        return R.layout.activity_manage_categories
    }

    private fun bindData() {
        adapter= TypeSortAdapter(originalNewsCategoryList as java.util.ArrayList<NewsCategory>,this)
        ryc_sort.layoutManager = LinearLayoutManager(this)
        ryc_sort.adapter = adapter
        val dragHelper = TypeDragHelper(DragCallback(this))
        dragHelper.attachToRecyclerView(ryc_sort)

    }

    override fun initView() {
        toolbar.setOnNavClick { onBackPressed() }
        viewModel.getLocalCategories()
        viewModel.newsCategoryListLiveLocal.observe(this, Observer {
            manageCategories(it)
        })
        bindData()

    }

    override fun start() {

    }

    override fun initData() {

    }

    private fun manageCategories(it: List<NewsCategory>?) {
        if (it!!.isNotEmpty()){
            originalNewsCategoryList.addAll(it)
            Timber.e("DATA FROM DB CATS"+Gson().toJson(originalNewsCategoryList))
        }else{
            Toast.makeText(this,"NO DATA LOADED",Toast.LENGTH_SHORT)
        }

    }


    override fun onBackPressed() {
        if (this!!.dataHasChanged!!)
            AlertDialog.Builder(this)
                .setTitle("SAVE CHANGES")
                .setMessage("APP WILL RESTART TO SAVE CHANGES")
                .setPositiveButton("YES") { dialog, _ ->
                    executeSortResult()
                    dialog.dismiss()
                    super.onBackPressed()
                }
                .setNegativeButton("CANCEL") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        else {
            super.onBackPressed()
        }
    }


    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        dataHasChanged=true
        Timber.e("ITEM POS  $fromPosition  $toPosition $dataHasChanged")
        adapter!!.onItemMove(fromPosition, toPosition)
        Collections.swap(originalNewsCategoryList,fromPosition,toPosition)


    }


    private fun executeSortResult() {
       Timber.e("NEW CATEGORY STRUC"+Gson().toJson(originalNewsCategoryList[0]))
       viewModel.persistCategories(originalNewsCategoryList)
    }
}
