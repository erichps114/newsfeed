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

class HeadLineModel : Serializable{
    @Expose
    var main : String = ""
}

class MultimediaModel : Serializable{
    @Expose
    var url : String = ""

}

data class NewsModel (
    var title:String  = "",
    var web_url : String = "",
    var snippet : String ="",
    var pub_date : String ="",
    var headline : HeadLineModel = HeadLineModel(),
    var multimedia : List<MultimediaModel> = mutableListOf()
) : Serializable{
    override fun equals(other: Any?): Boolean {
        if (other !is NewsModel) return false
        return other.web_url == this.web_url
    }

    override fun hashCode(): Int {
        return web_url.hashCode()
    }
}