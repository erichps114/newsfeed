package com.project.newsfeed

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.project.newsfeed.component.SwipeListener
import com.project.newsfeed.model.NewsModel
import com.project.newsfeed.utility.Storage
import com.project.newsfeed.utility.info
import com.project.newsfeed.utility.toast
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : AppCompatActivity(), SwipeListener {
    private val storage by lazy {Storage.getInstance(applicationContext)}
    private var mList : List<NewsModel> = mutableListOf()
    private var currentPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        mWebView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        mWebView.settings.setAppCachePath(applicationContext.cacheDir.absolutePath)
        mWebView.swipeListener = this
        mWebView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Storage.getInstance(applicationContext).saveCached(mList[currentPosition])
            }
        }
        if (intent.hasExtra("news_url")){
            mWebView.loadUrl(intent.getStringExtra("news_url"))
        }

        if (intent.hasExtra("currentPosition")){
            currentPosition = intent.getIntExtra("currentPosition",0)
        }

        mList = if (intent.hasExtra("callingActivity")){
            val callingAct = intent.getStringExtra("callingActivity")
            when {
                callingAct.contains("FavoriteActivity") -> Storage.getInstance(applicationContext).getFavoriteNews().toList()
                callingAct.contains("SearchActivity") -> Storage.getInstance(applicationContext).getRecentSearchResults()
                else -> Storage.getInstance(applicationContext).getRecentlySavedNews()
            }

        } else {
            Storage.getInstance(applicationContext).getRecentlySavedNews()
        }

        favoriteIcon.setOnClickListener {
            val news = mList[currentPosition]
            if (storage.getFavoriteNews().contains(news)){
                storage.removeFavoriteNews(news)
                toast("News removed from favorites")
            } else {
                storage.saveFavoriteNews(news)
                toast("News added to favorites")
            }
        }

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
}
