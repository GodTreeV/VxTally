package com.homework.vxtally.pages.bill

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.homework.vxtally.R
import com.homework.vxtally.base.DateAndTimePickers
import com.homework.vxtally.db.bean.Bill

@BindingAdapter("setImage")
fun setImage(view: ImageView, bill: Bill) {
    BitmapFactory.decodeFile(bill.icon)?.let {
        view.setImageBitmap(it)
    } ?: run {
        view.setImageResource(R.drawable.ic_launcher_foreground)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setBillOutOrInText")
fun setBillOutOrInText(textView: TextView, bill: Bill) {
    val textColor = if (bill.isOut) Color.RED else Color.GREEN
    val prefix = if (bill.isOut) "-" else "+"
    textView.setTextColor(textColor)
    textView.text = "${prefix}${bill.money}"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setBillTime")
fun setBillTime(textView: TextView, bill: Bill) {
    textView.text = DateAndTimePickers.getFormatTimestamp(bill.time)
}