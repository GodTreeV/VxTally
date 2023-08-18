package com.homework.vxtally.base

import android.content.Context
import android.graphics.drawable.Drawable

interface TabInfoProvider {
    fun getIcon(context: Context): Drawable
    fun getTitle(context: Context): String
}