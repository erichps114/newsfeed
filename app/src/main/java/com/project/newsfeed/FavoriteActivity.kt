package com.project.newsfeed

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.project.newsfeed.component.SnippetAdapter
import com.project.newsfeed.contract.MainContract
import com.project.newsfeed.model.NewsModel
import com.project.newsfeed.presenter.MainPresenter
import com.project.newsfeed.utility.Storage
import kotlinx.android.synthetic.main.activity_main.*

class FavoriteActivity : AppCompatActivity() {

    private val mList = mutableListOf<NewsModel>()
    private val mAdapter by lazy { SnippetAdapter(mList,this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
