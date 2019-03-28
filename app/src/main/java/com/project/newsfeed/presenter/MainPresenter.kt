package com.project.newsfeed.presenter

import android.content.Context
import com.project.newsfeed.contract.MainContract
import com.project.newsfeed.model.NewsModel
import com.project.newsfeed.model.ResponseModel
import com.project.newsfeed.rest.ApiInterface
import com.project.newsfeed.utility.Storage
import com.project.newsfeed.utility.info
import com.project.newsfeed.utility.isConnected
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter (private val mView : MainContract.View) : MainContract.Presenter {
    private var currentPage = 0
    private var isQuerying = false
    private var isCacheLoaded = false
    override fun getRecentNews(context: Context,isForceRefresh : Boolean) {
        if (!context.isConnected() ){
            if(isCacheLoaded){
                mView.onDataResult(mutableListOf(),0)
            }
            info("Load cache")
            isCacheLoaded = true
            val cachedNews = Storage.getInstance(context).getCached().toList()
            mView.onDataResult(cachedNews,cachedNews.size)
            return
        }


        if (isQuerying) return
        if (isForceRefresh) currentPage = 0
        isQuerying = true
        mView.showLoading(true)
        val callApi = ApiInterface.instance.getRecent(currentPage)
        callApi.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                mView.errorToast("Error getting response")
                mView.showLoading(false)
                isQuerying = false
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.code() == 200) {
                    currentPage++

                    val docs = response.body()?.response?.docs.orEmpty()
                    mView.onDataResult(docs,docs.size)

                } else {
                    mView.errorToast("Error getting response")
                }
                isQuerying = false
                mView.showLoading(false)
            }
        })
    }

    override fun saveList(list: List<NewsModel>, context : Context) {
        Storage.getInstance(context).saveRecentNews(list)
    }
}