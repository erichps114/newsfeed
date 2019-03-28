package com.project.newsfeed

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.project.newsfeed.component.SnippetAdapter
import com.project.newsfeed.contract.MainContract
import com.project.newsfeed.model.NewsModel
import com.project.newsfeed.presenter.MainPresenter
import com.project.newsfeed.utility.Storage
import com.project.newsfeed.utility.attachLoadMore
import com.project.newsfeed.utility.toast
import kotlinx.android.synthetic.main.activity_main.*
import android.media.MediaCodec.MetricsConstants.MODE
import android.provider.SearchRecentSuggestions
import android.app.SearchManager
import com.project.newsfeed.utility.SearchSuggestionProvider


class SearchActivity : AppCompatActivity() {

    private val mList = mutableListOf<NewsModel>()
    private val mAdapter by lazy { SnippetAdapter(mList,this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            val suggestions = SearchRecentSuggestions(
                this,
                SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE
            )
            suggestions.saveRecentQuery(query, null)


        }


        val itemPerRow = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 4
        rvFeed.layoutManager = GridLayoutManager(this,itemPerRow)
        rvFeed.adapter = mAdapter

        refresh.setOnRefreshListener {
            refresh.isRefreshing = false
            refresh()
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh(){
        mList.clear()
        mList.addAll(Storage.getInstance(applicationContext).getFavoriteNews())
        mAdapter.notifyDataSetChanged()
    }
}
