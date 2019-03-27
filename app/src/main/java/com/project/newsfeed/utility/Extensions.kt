package com.project.newsfeed.utility

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
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

fun Context.getDefaultPreferences() : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
inline fun SharedPreferences.editPref(operation:(SharedPreferences.Editor) -> Unit){
    val editor = this.edit()
    operation(editor)
    editor.apply()
}
fun SharedPreferences.setValue(key:String, value : Any?){
    when (value){
        is String? -> editPref { it.putString(key, value) }
        is Int -> editPref { it.putInt(key, value) }
        is Boolean -> editPref { it.putBoolean(key,value) }
        is Float -> editPref { it.putFloat(key,value) }
        is Long -> editPref { it.putLong(key,value) }
        else   -> throw UnsupportedOperationException("Hanya mendukung string, integer, bool, float dan long")
    }
}

inline fun <reified T:Any> SharedPreferences.getValue(key:String,defaultValue : T? = null) : T{
    return when(T::class){
        String::class -> getString(key, defaultValue as? String?:"") as T
        Int::class -> getInt(key, defaultValue as? Int?:-1) as T
        Boolean::class -> getBoolean(key, defaultValue as? Boolean?:false) as T
        Float::class -> getFloat(key, defaultValue as? Float?:-1f) as T
        Long::class ->getLong(key, defaultValue as? Long?:-1) as T
        else -> throw UnsupportedOperationException("Hanya mendukung string, integer, bool, float dan long")
    }
}

fun Context.toast(string: String) = Handler(Looper.getMainLooper()).post{ Toast.makeText(this,string, Toast.LENGTH_SHORT).show()}
fun Context.isConnected() : Boolean{
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return (cm.activeNetworkInfo!= null && cm.activeNetworkInfo.isConnected)
}
fun Any.info (msg : String, tag : String = javaClass.simpleName) = Log.i(tag,msg)
fun Any.debug (msg : String , tag : String = javaClass.simpleName) = Log.d(tag,msg)
fun Any.error (msg : String , tag : String = javaClass.simpleName)= Log.e(tag,msg)
fun Any.warning (msg : String , tag : String = javaClass.simpleName) = Log.w(tag,msg)