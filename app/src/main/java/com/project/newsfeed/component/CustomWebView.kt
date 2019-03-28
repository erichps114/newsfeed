package com.project.newsfeed.component

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.webkit.WebView

/**
 * Created by ErichPS on 27/03/2019.
 */

class CustomWebView : WebView{
    var swipeListener : SwipeListener? = null
    constructor(ctx : Context) : super(ctx)
    constructor(ctx : Context, attrs : AttributeSet) : super(ctx,attrs)
    constructor(ctx : Context, attrs : AttributeSet, defStyles : Int) : super(ctx,attrs,defStyles)
    constructor(ctx : Context, attrs : AttributeSet, defStyles : Int, defStyleRef : Int) : super(ctx,attrs,defStyles,defStyleRef)

    private val swipeThreshold = 100
    private val swipeVelocityThreshold = 100


    private val gestureDetector = GestureDetector(context,object : GestureDetector.SimpleOnGestureListener(){
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            if (e1 == null || e2 == null) return super.onFling(e1, e2, velocityX, velocityY)
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > swipeThreshold && Math.abs(velocityX) > swipeVelocityThreshold) {
                        if (diffX > 0) {
                            swipeListener?.onSwipeRight()
                        } else {
                            swipeListener?.onSwipeLeft()
                        }
                        result = true
                    }
                } else if (Math.abs(diffY) > swipeThreshold && Math.abs(velocityY) > swipeVelocityThreshold) {
                    if (diffY > 0) {
                        flingScroll(0,-2000)
                    } else {
                        flingScroll(0,2000)
                    }
                    result = false
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }
    })


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }
}

interface SwipeListener{
    fun onSwipeLeft()
    fun onSwipeRight()
}