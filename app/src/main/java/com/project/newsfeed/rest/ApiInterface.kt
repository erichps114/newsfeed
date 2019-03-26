package com.project.newsfeed.rest

import com.project.newsfeed.model.ResponseModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("svc/search/v2/articlesearch.json")
    fun searchArticles(@Query("q")query : String, @Query("page")currentPage:Int, @Query("api-key") apiKey : String = "7tHQW9v8rNg62sWmdxYmOyKXcdiFlkTR") : Call<ResponseModel>

    @GET("svc/news/v3/content/all/all.json?")
    fun getRecent(@Query("page")currentPage:Int, @Query("api-key") apiKey : String = "7tHQW9v8rNg62sWmdxYmOyKXcdiFlkTR") : Call<ResponseModel>
    companion object {

        val instance: ApiInterface by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.nytimes.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().also{it.level = HttpLoggingInterceptor.Level.BODY})
                        .build()
                )
                .build()
            retrofit.create(ApiInterface::class.java)
        }
    }
}