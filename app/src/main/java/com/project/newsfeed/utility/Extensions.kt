package com.project.newsfeed.utility

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by ErichPS on 27/03/2019.
 */

fun RecyclerView.attachLoadMore(mList : List<Any>, onLoadMore : ()->Unit){
    if (layoutManager !is LinearLayoutManager) throw IllegalArgumentException("Only support GridLayoutManager")
    this.addOnScrollListener(object : RecyclerView.OnScrollListener(){
        var isLoadMore = false
        val mLayoutManager = layoutManager as GridLayoutManager
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lastVisibleItem = mLayoutManager.findLastVisibleItemPosition()

            if (lastVisibleItem >= mList.size - 1  && !isLoadMore){
                isLoadMore = true
                onLoadMore()
            } else if (lastVisibleItem < mList.size - 1){
                isLoadMore = false
            }
        }
    })
}