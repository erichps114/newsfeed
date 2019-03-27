package com.project.newsfeed

import android.os.Bundle
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import com.project.newsfeed.component.CustomWebView
import com.project.newsfeed.component.SwipeListener
import com.project.newsfeed.model.NewsModel
import com.project.newsfeed.utility.Storage
import com.project.newsfeed.utility.info

class WebViewActivity : AppCompatActivity(), SwipeListener {
    private val mWebView by lazy { CustomWebView(this,this) }
    private var mList : List<NewsModel> = mutableListOf()
    private var currentPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mWebView)

        if (intent.hasExtra("news_url")){
            mWebView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            mWebView.settings.setAppCachePath(applicationContext.cacheDir.absolutePath)
            mWebView.loadUrl(intent.getStringExtra("news_url"))
        }

        if (intent.hasExtra("currentPosition")){
            currentPosition = intent.getIntExtra("currentPosition",0)
        }

        mList = Storage.getInstance(applicationContext).getRecentlySavedNews()

    }

    override fun onBackPressed() {
        if (mWebView.canGoBack()) mWebView.goBack()
        else super.onBackPressed()
    }

    override fun onSwipeLeft() {
        if (mList.isNotEmpty() && currentPosition + 1 < mList.size){
            currentPosition++
            mWebView.loadUrl(mList[currentPosition].web_url)
        }
    }

    override fun onSwipeRight() {
        if (currentPosition > 0 && mList.isNotEmpty()){
            currentPosition--
            mWebView.loadUrl(mList[currentPosition].web_url)
        }
    }

    override fun onSwipeTop() {
        info("swipe top")
    }

    override fun onSwipeBottom() {
        info("swipe bottom")
    }
}
