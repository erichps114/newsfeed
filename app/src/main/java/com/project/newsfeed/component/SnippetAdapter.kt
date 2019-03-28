package com.project.newsfeed.component

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.newsfeed.R
import com.project.newsfeed.WebViewActivity
import com.project.newsfeed.model.NewsModel
import kotlinx.android.synthetic.main.news_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class SnippetAdapter(private val mList : List<NewsModel>, val context : Context) : RecyclerView.Adapter<NewsViewHolder>(){
    private val calendar = Calendar.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false))
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val model = mList[position]
        holder.title.text = model.headline.main
        holder.snippet.text = model.snippet

        val date = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ").parse(model.pub_date)
        calendar.time = date
        holder.date.text = ("${calendar.get(Calendar.DAY_OF_MONTH)} / ${calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault())}")
        if (model.multimedia.isNotEmpty())
            Glide.with(holder.itemView).load("http://www.nytimes.com/${model.multimedia[0].url}" ).into(holder.thumbnail)
        else {
            holder.thumbnail.setImageResource(android.R.color.transparent)
        }

        holder.itemView.setOnClickListener {
            Intent(context,WebViewActivity::class.java).also {
                it.putExtra("news_url", model.web_url)
                it.putExtra("currentPosition",position)
                it.putExtra("callingActivity",context.javaClass.simpleName)
                context.startActivity(it)
            }
        }
    }
}

class NewsViewHolder(v : View) : RecyclerView.ViewHolder(v){
    val title = v.title
    val snippet = v.detail
    val thumbnail = v.thumbnail
    val date = v.date
}