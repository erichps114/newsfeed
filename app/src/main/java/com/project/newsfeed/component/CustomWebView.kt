package com.project.newsfeed.component

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.webkit.WebView
import com.project.newsfeed.utility.toast

/**
 * Created by ErichPS on 27/03/2019.
 */

class CustomWebView(private val ctx : Context) : WebView(ctx){


    private val gestureDetector = GestureDetector(ctx,object : GestureDetector.SimpleOnGestureListener(){
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            return if (e1!=null && e2 !=null){
                if (e1.rawX > e2.rawX){
                    ctx.toast("Swipe left")
                } else {
                    ctx.toast("swipe righrt")
                }

                true
            } else {
                super.onFling(e1, e2, velocityX, velocityY)
            }
        }
    })


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }
}