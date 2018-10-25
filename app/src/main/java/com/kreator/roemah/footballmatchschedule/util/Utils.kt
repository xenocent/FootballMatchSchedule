package com.kreator.roemah.footballmatchschedule.util

import android.view.View
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun TextView.setFormattedDate(date: String?) {
    date?.let {
        val oldDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(date)
        val newDate = SimpleDateFormat("EEE, dd MMM yyyy", Locale("in", "ID")).format(oldDate)

        text = newDate
    }
}

fun TextView.setFormattedDateFav(date: String?) {
    date?.let {
        val oldDate = SimpleDateFormat("dd/MM/yy", Locale.US).parse(date)
        val newDate = SimpleDateFormat("EEE, dd MMM yyyy", Locale("in", "ID")).format(oldDate)

        text = newDate
    }
}