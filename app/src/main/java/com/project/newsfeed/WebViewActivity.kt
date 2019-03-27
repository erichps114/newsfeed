package com.project.newsfeed

import android.os.Bundle
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import com.project.newsfeed.component.CustomWebView

class WebViewActivity : AppCompatActivity() {
    private val mWebView by lazy { CustomWebView(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mWebView)

        if (intent.hasExtra("news_url")){
            mWebView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            mWebView.settings.javaScriptEnabled = true
            mWebView.loadUrl(intent.getStringExtra("news_url"))
        }

    }

    override fun onBackPressed() {
        if (mWebView.canGoBack()) mWebView.goBack()
        else super.onBackPressed()
    }

}
