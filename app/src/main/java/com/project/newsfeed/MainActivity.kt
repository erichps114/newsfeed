package com.project.newsfeed

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.newsfeed.component.SnippetAdapter
import com.project.newsfeed.model.NewsModel
import com.project.newsfeed.model.ResponseModel
import com.project.newsfeed.rest.ApiInterface
import com.project.newsfeed.utility.attachLoadMore
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val mList = mutableListOf<NewsModel>()
    private val mAdapter by lazy { SnippetAdapter(mList) }
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemPerRow = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 4
        rvFeed.layoutManager = GridLayoutManager(this,itemPerRow)
        rvFeed.adapter = mAdapter

        performQuery()
        rvFeed.attachLoadMore(mList){
            onLoadMore()
        }


    }

    private fun performQuery(){
        val callApi = ApiInterface.instance.getRecent(currentPage)
        callApi.enqueue(object : Callback<ResponseModel>{
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.e("ERICH", "ERROR")
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.code() != 200) {Log.e("ERICH","Failed"); return}
                currentPage++
                val lastPos = mList.size

                val docs = response.body()?.response?.docs
                if (!docs.isNullOrEmpty()){
                    docs.forEach {mList.add(it)}
                    mAdapter.notifyItemRangeInserted(lastPos,docs.size)
                }

            }
        })
    }

    private fun onLoadMore(){
        performQuery()
    }
}
