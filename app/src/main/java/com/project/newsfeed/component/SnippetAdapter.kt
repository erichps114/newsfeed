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

class SnippetAdapter(private val mList : List<NewsModel>, val context : Context) : RecyclerView.Adapter<NewsViewHolder>(){
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

        holder.itemView.setOnClickListener {
            Intent(context,WebViewActivity::class.java).also {
                it.putExtra("news_url", model.web_url)
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