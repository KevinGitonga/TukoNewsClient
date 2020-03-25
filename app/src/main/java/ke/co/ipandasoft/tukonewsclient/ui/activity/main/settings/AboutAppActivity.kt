package ke.co.ipandasoft.tukonewsclient.ui.activity.main.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.appbar.AppBarLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ke.co.ipandasoft.tukonewsclient.R
import ke.co.ipandasoft.tukonewsclient.TukoNewsApp.Companion.context
import ke.co.ipandasoft.tukonewsclient.data.models.LibrarySource
import ke.co.ipandasoft.tukonewsclient.ui.adapters.LibrarySourceAdapter
import ke.co.ipandasoft.tukonewsclient.ui.adapters.OnItemClickListener
import ke.co.ipandasoft.tukonewsclient.ui.base.BaseActivity
import ke.co.ipandasoft.tukonewsclient.utils.AppUtils
import ke.co.ipandasoft.tukonewsclient.utils.getStatusBarHeight
import ke.co.ipandasoft.tukonewsclient.utils.setStatusTransparent
import kotlinx.android.synthetic.main.activity_about.*
import timber.log.Timber
import kotlin.math.absoluteValue

class AboutAppActivity : BaseActivity(),OnItemClickListener {

    private val sources = ArrayList<LibrarySource>().apply {
        add(LibrarySource("KOIN DI - KOIN DI", "https://github.com/InsertKoinIO/koin"))
        add(LibrarySource("Timber - Logger", "https://github.com/JakeWharton/timber"))
        add(LibrarySource("retrofit - Square", "https://github.com/square/retrofit"))
        add(LibrarySource("glide - bumptech", "https://github.com/bumptech/glide"))
        add(LibrarySource("Easing Animations- Daimajia", "https://github.com/daimajia/AnimationEasingFunctions"))
        add(LibrarySource("Ocpsoft - Pretty Time", "https://github.com/ocpsoft/prettytime"))
        add(LibrarySource("Multiple Status View", "https://github.com/qyxxjd/MultipleStatusView"))
    }

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        LibrarySourceAdapter(sources,this)
    }

    private enum class CollapsingToolbarLayoutState {
        EXPANDED, COLLAPSED, INTERMEDIATE
    }

    private var state = CollapsingToolbarLayoutState.EXPANDED


    override fun layoutId(): Int {
        return R.layout.activity_about
    }

    override fun initData() {
    }

    override fun initView() {
        tv_version.text = getString(R.string.version).format(AppUtils.getVerName(this))
        adapter.setOnItemClickListener(this)
        bindData()

    }

    private fun bindData() {
        toolbar_about.setNavigationOnClickListener {
            onBackPressed()
        }
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                if (state != CollapsingToolbarLayoutState.EXPANDED) {
                    state = CollapsingToolbarLayoutState.EXPANDED
                    toolbar_about.title = ""
                }
            } else if (verticalOffset.absoluteValue >= appBarLayout.totalScrollRange) {
                if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                    state = CollapsingToolbarLayoutState.COLLAPSED
                    toolbar_about.title = getString(R.string.about)
                }
            }
        })

        ryc_sources.layoutManager = LinearLayoutManager(this)
        ryc_sources.adapter = adapter
    }

    override fun start() {

    }

    override fun onItemClick(obj: Any?, position: Int) {
      Timber.e("item clicked $position")
        val data=obj as LibrarySource
        val intent=Intent("android.intent.action.VIEW", Uri.parse(data.address))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
