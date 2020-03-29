package ke.co.ipandasoft.tukonewsclient.ui.activity.main.browser

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import ke.co.ipandasoft.tukonewsclient.R
import ke.co.ipandasoft.tukonewsclient.ui.base.BaseActivity
import ke.co.ipandasoft.tukonewsclient.utils.isOpenApp
import kotlinx.android.synthetic.main.browser_activity.*
import org.adblockplus.libadblockplus.android.AdblockEngine
import org.adblockplus.libadblockplus.android.settings.AdblockHelper
import timber.log.Timber

class BrowserActivity :BaseActivity(){

    private val url by lazy(LazyThreadSafetyMode.NONE) {
        intent.getStringExtra("news_url")
    }

    private val title by lazy(LazyThreadSafetyMode.NONE) {
        intent.getStringExtra("newsTitle")
    }
    private lateinit var webSetting: WebSettings


    override fun layoutId(): Int {
        return R.layout.browser_activity
    }

    override fun initData() {
        initAdBlocker()
        val isArticle=intent.getBooleanExtra("isArticle",false)

        Timber.e("NEWS TITLE  $isArticle")

        if (isArticle){
            toolbar.title=title

        }
    }

    private fun initAdBlocker() {

    }

    override fun initView() {
      bindViews()
    }

    private fun bindViews() {
        toolbar.apply {
            setOnNavClick { onBackPressed() }
            setOnMenuClick { }
        }

        bindWebView()
    }

    private fun bindWebView() {
        web_view.run {
            removeJavascriptInterface("searchBoxJavaBridge_")
            removeJavascriptInterface("accessibilityTraversal")
            removeJavascriptInterface("accessibility")

            // use shared filters data (not to increase memory consumption)
            setProvider(AdblockHelper.get().provider)
        }


        webSetting = web_view.settings
        webSetting.apply {
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            loadsImagesAutomatically = true
            defaultTextEncodingName = "utf-8"
            domStorageEnabled = true
            databaseEnabled = true
            setAppCacheEnabled(true)
            if (Build.VERSION.SDK_INT >= 21) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
        web_view.loadUrl(url)
        web_view.webViewClient = object : WebViewClient() {
            @Suppress("DEPRECATION", "OverridingDeprecatedMember")
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                url ?: return true
                return if (url.isOpenApp()) {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    } catch (e: Exception) {
                    }
                    true
                } else {
                    super.shouldOverrideUrlLoading(view, url)
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
            }

        }
        web_view.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String) {

            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100) {
                    progress_bar.alpha = 0f
                } else {
                    progress_bar.alpha = 1f
                    progress_bar.progress = newProgress
                }
            }
        }
    }

    override fun start() {

    }


    override fun onBackPressed() {
        super.onBackPressed()
        if (web_view.canGoBack())
            web_view.goBack()
        else {
            super.onBackPressed()
        }
    }

}