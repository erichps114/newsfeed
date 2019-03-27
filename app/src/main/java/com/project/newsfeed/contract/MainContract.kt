package com.project.newsfeed.contract

import android.content.Context
import com.project.newsfeed.model.NewsModel

interface MainContract {
    interface View{
        fun showLoading(isShow : Boolean)
        fun onDataResult (list : List<NewsModel>, totalResult : Int)
        fun errorToast(message : String)
    }

    interface Presenter{
        fun getRecentNews(context: Context,isForceRefresh : Boolean)
        fun saveList(list:List<NewsModel>, context : Context)
    }
}