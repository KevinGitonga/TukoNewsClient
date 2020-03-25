package ke.co.ipandasoft.tukonewsclient.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment(){

    /**
     * Whether the view is loaded
     */
    private var isViewPrepare = false
    /**
     * Has the data been loaded
     */
    private var hasLoadData = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(),null)
    }



    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView()
        lazyLoadDataIfPrepared()

    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoad()
    }


    /**
     * LOAD VIEW LAYOUT
     */
    @LayoutRes
    abstract fun getLayoutId():Int

    /**
     * initialization ViewI
     */
    abstract fun initView()

    /**
     * Lazy loading
     */
    abstract fun lazyLoad()

    override fun onDestroy() {
        super.onDestroy()
    }

}