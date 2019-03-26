package com.project.newsfeed

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
                Log.e("ERICH", "ERROR")
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.code() != 200) {Log.e("ERICH","Failed"); return}
                response.body()?.response?.docs?.forEach {
                    Log.i("ERICH","""
                    Headline : ${it.headline}
                    Snipper : ${it.snippet}
                    Web_Url : ${it.web_url}
                    Pub_date : ${it.pub_date}
                """.trimIndent())
                }

            }
        })
    }
}
