package ke.co.ipandasoft.tukonewsclient.ui.activity.main.bookmarks


import android.os.Bundle
import androidx.fragment.app.Fragment
import ke.co.ipandasoft.tukonewsclient.R
import ke.co.ipandasoft.tukonewsclient.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_book_marks.*
import kotlinx.android.synthetic.main.toolbar_layout.*

/**
 * A simple [Fragment] subclass.
 */
class BookMarksFragment : BaseFragment() {

    companion object {
        fun getInstance(): BookMarksFragment {
            val fragment =
                BookMarksFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_book_marks
    }

    override fun initView() {
        toolbar_title.text = "BOOKMARKS"
        inflateList()
    }

    override fun lazyLoad() {
    }

    private fun inflateList() {
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.likedContainer,BookMarksListFragment.getInstance()).commit()
    }


}
