package com.project.newsfeed

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.project.newsfeed.model.ResponseModel
import com.project.newsfeed.rest.ApiInterface
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemPerRow = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 4
        rvFeed.layoutManager = GridLayoutManager(this,itemPerRow)

        val callApi = ApiInterface.instance.searchArticles("indonesia",0)
        callApi.enqueue(object : Callback<ResponseModel>{
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {

            }
        })
    }
}
