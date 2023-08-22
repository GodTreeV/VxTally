package com.homework.vxtally.pages.bill

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.homework.vxtally.R
import com.homework.vxtally.base.DateAndTimePickers
import com.homework.vxtally.db.bean.Bill

@BindingAdapter("setImage")
fun setImage(view: ImageView, bill: Bill) {
    BitmapFactory.decodeFile(bill.icon)?.let {
        view.setImageBitmap(it)
    } ?: run {
        Glide.with(view.context)
            .load("https://img1.baidu.com/it/u=139610508,1614409688&fm=253&fmt=auto&app=138&f=PNG?w=500&h=500")
            .into(view)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setBillOutOrInText")
fun setBillOutOrInText(textView: TextView, bill: Bill) {
    val textColor =
        if (bill.isOut) textView.context.getColor(R.color.md_theme_dark_error) else textView.context.getColor(
            R.color.md_theme_light_primary
        )
    val prefix = if (bill.isOut) "-" else "+"
    textView.setTextColor(textColor)
    textView.text = "${prefix}${bill.money}"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setBillTime")
fun setBillTime(textView: TextView, bill: Bill) {
    textView.text = DateAndTimePickers.getFormatTimestamp(bill.time)
}