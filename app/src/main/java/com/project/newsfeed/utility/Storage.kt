package com.project.newsfeed.utility

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.newsfeed.model.NewsModel

class Storage(private val context : Context) {
    companion object : SingletonHolder<Storage, Context>(::Storage)

    private val gson = Gson()
    fun getRecentlySavedNews() : List<NewsModel> {
        val typeToken = object : TypeToken<List<NewsModel>>(){}.type
        return gson.fromJson<List<NewsModel>>(context.getDefaultPreferences().getValue("recent","{}"),typeToken).orEmpty()
    }

    fun getFavoriteNews() : List<NewsModel> {
        val typeToken = object : TypeToken<List<NewsModel>>(){}.type
        return gson.fromJson<List<NewsModel>>(context.getDefaultPreferences().getValue("favorite","{}"),typeToken).orEmpty()
    }

    fun saveRecentNews (list : List<NewsModel>){
        val jsonRep = gson.toJson(list)
        context.getDefaultPreferences().setValue("recent",jsonRep)
    }

    fun saveFavoriteNews (list : List<NewsModel>){
        val jsonRep = gson.toJson(list)
        context.getDefaultPreferences().setValue("favorite",jsonRep)
    }
}