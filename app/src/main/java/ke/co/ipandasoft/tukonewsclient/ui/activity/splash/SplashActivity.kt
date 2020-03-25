package ke.co.ipandasoft.tukonewsclient.ui.activity.splash

import ke.co.ipandasoft.tukonewsclient.R
import ke.co.ipandasoft.tukonewsclient.ui.base.BaseActivity
import org.jetbrains.anko.longToast
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import android.animation.ValueAnimator
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import com.google.gson.Gson
import ke.co.ipandasoft.tukonewsclient.data.models.NewsCategory
import ke.co.ipandasoft.tukonewsclient.data.remote.Response
import ke.co.ipandasoft.tukonewsclient.ui.activity.main.HomeActivity
import ke.co.ipandasoft.tukonewsclient.utils.NavigationUtils
import kotlinx.android.synthetic.main.splash_layout.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


class SplashActivity : BaseActivity() {

    private var newsCategoryList: MutableList<NewsCategory> = arrayListOf()
    private val viewModel by viewModel<SplashViewModel>()


    private var isShowingRubberEffect: Boolean = false

    override fun layoutId(): Int {
        return R.layout.splash_layout
    }

    override fun initData() {
    }

    override fun initView() {
        initAnimation()
   }

    override fun start() {
        viewModel.getLocalCategories()
        viewModel.newsCategoryListLiveLocal.observe(this, Observer {
            handleResponse(it)
        })
   }

    private fun handleResponse(it: List<NewsCategory>?) {
        Timber.e("CATS FROM DB LOCAL"+Gson().toJson(it))
        if (it!!.isEmpty()){
            viewModel.getRemoteDataCategories()
            viewModel.newsCategoryListLive.observe(this, Observer {
                handleState(it)
            })
        }else{
            navigateToMain()
        }

    }

    private fun handleState(result: Response<List<NewsCategory>>) {

        when (result) {

            is Response.Loading -> {
                Timber.e("LOADING DATA FROM SERVERS")
            }
            is Response.Success -> {
                newsCategoryList.addAll(result.data)
                viewModel.persistCategories(newsCategoryList)
                Timber.e(newsCategoryList.size.toString())
                navigateToMain()
            }
            is Response.Error -> {
                Timber.e("ERROR LOADING DATA FROM SERVERS")
            }
        }
    }


    private fun initAnimation() {
        startLogoInner1()
        startLogoOuterAndAppName()
    }

    private fun startLogoInner1() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.anim_top_in)
        logo_inner_iv.startAnimation(animation)
    }

    private fun startLogoOuterAndAppName() {
        val valueAnimator=ValueAnimator.ofFloat(0F,1F)
        valueAnimator.duration = 1000
        valueAnimator.addUpdateListener { animation ->
            val fraction = animation.animatedFraction


            if (fraction >= 0.8 && !isShowingRubberEffect) {
                isShowingRubberEffect = true
                startLogoOuter()
                startShowAppName()
            } else if (fraction >= 0.95) {
                valueAnimator.cancel()
                startLogoInner2()
            }
        }
        valueAnimator.start()
    }

    private fun navigateToMain() {
       Handler().postDelayed({
            finish()
            NavigationUtils.navigate(this@SplashActivity,HomeActivity::class.java)
        }, 2000)
    }

    private fun startLogoOuter() {
        YoYo.with(Techniques.RubberBand).duration(1000).playOn(logo_outer_iv)
    }

    private fun startShowAppName() {
        YoYo.with(Techniques.FadeIn).duration(1000).playOn(app_name_tv)
    }

    private fun startLogoInner2() {
        YoYo.with(Techniques.Bounce).duration(1000).playOn(logo_inner_iv)
    }




}