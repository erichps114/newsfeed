package com.project.newsfeed.presenter

import android.content.Context
import com.project.newsfeed.contract.MainContract
import com.project.newsfeed.contract.SearchContract
import com.project.newsfeed.model.NewsModel
import com.project.newsfeed.model.ResponseModel
import com.project.newsfeed.rest.ApiInterface
import com.project.newsfeed.utility.Storage
import com.project.newsfeed.utility.info
import com.project.newsfeed.utility.isConnected
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPresenter (private val mView : SearchContract.View) : SearchContract.Presenter {
    private var currentPage = 0
    private var isQuerying = false
    override fun searchNews(context: Context,query : String) {
        if (!context.isConnected() ){
            mView.errorToast("Not connected")
            return
        }


        if (isQuerying) return
        isQuerying = true
        mView.showLoading(true)
        val callApi = ApiInterface.instance.searchArticles(query,currentPage)
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

    override fun saveList(list: List<NewsModel>, context: Context) {
        Storage.getInstance(context.applicationContext).saveRecentSearchQuery(list)
    }
}