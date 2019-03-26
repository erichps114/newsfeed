package com.project.newsfeed.model

import com.google.gson.annotations.Expose
import java.io.Serializable

class ResponseModel {
    @Expose
    var response : Container = Container()
}

class Container {
    @Expose
    var docs : List<NewsModel> = mutableListOf()
}

class HeadLineModel{
    @Expose
    var main : String = ""
}

data class NewsModel (
    var web_url : String = "",
    var snippet : String ="",
    var pub_date : String ="",
    var headline : HeadLineModel = HeadLineModel()
) : Serializable