package com.homework.vxtally.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.homework.vxtally.pages.EmptyFragment

object Fragments {
    fun <T : Fragment> newInstance(clazz: Class<T>, extras: (Bundle) -> Unit): T {
        return clazz.newInstance().apply {
            arguments = Bundle().apply {
                extras(this)
            }
        }
    }
}