package com.project.newsfeed

import android.app.SearchManager
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.project.newsfeed.component.SnippetAdapter
import com.project.newsfeed.contract.SearchContract
import com.project.newsfeed.model.NewsModel
import com.project.newsfeed.presenter.SearchPresenter
import com.project.newsfeed.utility.SearchSuggestionProvider
import com.project.newsfeed.utility.attachLoadMore
import com.project.newsfeed.utility.toast
import kotlinx.android.synthetic.main.activity_main.*


class SearchActivity : AppCompatActivity(), SearchContract.View {

    private val mList = mutableListOf<NewsModel>()
    private val mAdapter by lazy { SnippetAdapter(mList,this) }
    private val mPresenter : SearchContract.Presenter = SearchPresenter(this)
    private var query = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (Intent.ACTION_SEARCH == intent.action) {
            query = intent.getStringExtra(SearchManager.QUERY)
            val suggestions = SearchRecentSuggestions(
                this,
                SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE
            )
            suggestions.saveRecentQuery(query, null)


        }


        val itemPerRow = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 4
        rvFeed.layoutManager = GridLayoutManager(this,itemPerRow)
        rvFeed.adapter = mAdapter
        rvFeed.attachLoadMore(mList){
            onLoadMore()
        }

        refresh.setOnRefreshListener {
            refresh.isRefreshing = false
        }

        onLoadMore()
    }
    private fun onLoadMore(){
        mPresenter.searchNews(applicationContext,query)
    }


    override fun showLoading(isShow: Boolean) {

    }

    override fun onDataResult(list: List<NewsModel>, totalResult: Int) {
        if (totalResult == 0) return
        val lastIndex = mList.size
        mList.addAll(list)
        mAdapter.notifyItemRangeInserted(lastIndex,totalResult)

        mPresenter.saveList(mList,applicationContext)
    }

    override fun errorToast(message: String) {
        toast(message)
    }
}
