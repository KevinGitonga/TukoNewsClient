package ke.co.ipandasoft.tukonewsclient.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ke.co.ipandasoft.tukonewsclient.ui.activity.main.HomeActivity

abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        initData()
        initView()
        start()
    }



    /**
     *  GET LAYOUT
     */
    abstract fun layoutId(): Int

    /**
     * INITDATA
     */
    abstract fun initData()

    /**
     * INIT ViewS
     */
    abstract fun initView()

    /**
     * START ACTIVITY
     */
    abstract fun start()

}