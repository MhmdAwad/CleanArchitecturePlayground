package com.mhmdawad.cleanarchitectureplayground.common

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mhmdawad.cleanarchitectureplayground.R


fun View.hide(){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}

fun TextView.changeColorIfIsActive(isActive: Boolean){
    if (isActive) {
        text = resources.getString(R.string.active)
        setTextColor(
            ContextCompat.getColor(context, R.color.green)
        )
    } else {
        text = resources.getString(R.string.inActive)
        setTextColor(
            ContextCompat.getColor(context, R.color.red)
        )
    }
}