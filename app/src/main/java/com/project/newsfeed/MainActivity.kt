package com.project.newsfeed

import android.app.Activity
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
import com.project.newsfeed.utility.attachLoadMore
import com.project.newsfeed.utility.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    private val mList = mutableListOf<NewsModel>()
    private val mAdapter by lazy { SnippetAdapter(mList,this) }
    private val mPresenter : MainContract.Presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_LOCAL)

        val itemPerRow = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 4
        rvFeed.layoutManager = GridLayoutManager(this,itemPerRow)
        rvFeed.adapter = mAdapter
        rvFeed.attachLoadMore(mList){
            onLoadMore()
        }

        refresh.setOnRefreshListener {
            onRefresh()
        }

        onLoadMore()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId ){
            R.id.favorite ->{
                Intent(this, FavoriteActivity::class.java).also{startActivity(it)}
                return true
            }

            R.id.search -> {
                super.onSearchRequested()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onLoadMore(isForceRefresh : Boolean = false){
        mPresenter.getRecentNews(applicationContext,isForceRefresh)
    }

    private fun onRefresh(){
        mList.clear()
        mAdapter.notifyDataSetChanged()
        onLoadMore(true)
    }

    override fun showLoading(isShow: Boolean) {
        //TODO : Show loading
    }

    override fun errorToast(message: String) {
        toast(message)
    }

    override fun onDataResult(list: List<NewsModel>, totalResult: Int) {
        refresh.isRefreshing = false
        if (totalResult == 0) return
        val lastIndex = mList.size
        mList.addAll(list)
        mAdapter.notifyItemRangeInserted(lastIndex,totalResult)

        mPresenter.saveList(mList,applicationContext)
    }
}
