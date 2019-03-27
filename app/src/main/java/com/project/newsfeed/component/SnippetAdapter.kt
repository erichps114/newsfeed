package com.project.newsfeed.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.newsfeed.R
import com.project.newsfeed.model.NewsModel
import kotlinx.android.synthetic.main.news_item.view.*

class SnippetAdapter(val mList : List<NewsModel>) : RecyclerView.Adapter<NewsViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false))
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val model = mList[position]
        holder.title.text = model.headline.main
        holder.snippet.text = model.snippet
        holder.date.text = model.pub_date
        if (model.multimedia.isNotEmpty())
            Glide.with(holder.itemView).load("http://www.nytimes.com/${model.multimedia[0].url}" ).into(holder.thumbnail)
        else {
            holder.thumbnail.setImageResource(R.mipmap.ic_launcher)
        }
    }
}

class NewsViewHolder(v : View) : RecyclerView.ViewHolder(v){
    val title = v.title
    val snippet = v.detail
    val thumbnail = v.thumbnail
    val date = v.date
}