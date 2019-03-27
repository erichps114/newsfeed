package com.project.newsfeed.contract

import com.project.newsfeed.model.NewsModel

interface MainContract {
    interface View{
        fun showLoading(isShow : Boolean)
        fun onDataResult (list : List<NewsModel>, totalResult : Int)
        fun errorToast(message : String)
    }

    interface Presenter{
        fun getRecentNews(isForceRefresh : Boolean)
    }
}