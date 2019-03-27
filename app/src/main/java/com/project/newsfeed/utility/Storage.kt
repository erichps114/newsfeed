package com.project.newsfeed.utility

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.newsfeed.model.NewsModel

class Storage(private val context : Context) {
    companion object : SingletonHolder<Storage, Context>(::Storage)

    private val gson = Gson()
    private val mRecentNews = mutableListOf<NewsModel>()
    private val mFavoriteNews = mutableSetOf<NewsModel>()
    fun getRecentlySavedNews() : List<NewsModel> {
        if (mRecentNews.isNotEmpty()) return mRecentNews.toList()
        val typeToken = object : TypeToken<List<NewsModel>>(){}.type
        return gson.fromJson<List<NewsModel>>(context.getDefaultPreferences().getValue("recent","[]"),typeToken).orEmpty()
    }

    fun getFavoriteNews() : List<NewsModel> {
        if (mFavoriteNews.isNotEmpty()) return mFavoriteNews.toList()
        val typeToken = object : TypeToken<HashSet<NewsModel>>(){}.type
        return gson.fromJson<HashSet<NewsModel>>(context.getDefaultPreferences().getValue("favorite","[]"),typeToken).orEmpty().toList()
    }

    fun saveRecentNews (list : List<NewsModel>){
        mRecentNews.clear()
        mRecentNews.addAll(list)
        val jsonRep = gson.toJson(list)
        context.getDefaultPreferences().setValue("recent",jsonRep)
    }

    fun saveFavoriteNews (list : List<NewsModel>){
        mFavoriteNews.clear()
        mFavoriteNews.addAll(list)
        val jsonRep = gson.toJson(list)
        context.getDefaultPreferences().setValue("favorite",jsonRep)
    }

    fun getCached() : HashSet<NewsModel> {
        val typeToken = object : TypeToken<HashSet<NewsModel>>(){}.type
        return gson.fromJson<HashSet<NewsModel>>(context.getDefaultPreferences().getValue("cached","[]"),typeToken)
    }

    fun saveCached (news : NewsModel){
        val set = getCached()
        set.add(news)
        val jsonRep = gson.toJson(set)
        context.getDefaultPreferences().setValue("cached",jsonRep)
    }
}