package com.homework.vxtally.pages

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.homework.vxtally.R
import com.homework.vxtally.base.BaseFragment
import com.homework.vxtally.base.KEY_TITLE
import com.homework.vxtally.databinding.FragmentEmptyBinding
import com.ysw.android.extensions.logE
import com.ysw.android.extensions.viewbinding.BaseViewBindingFragment

open class EmptyFragment : BaseFragment<FragmentEmptyBinding>() {

    override var layoutRes: Int
        get() = R.layout.fragment_empty
        set(value) {}

    override fun getIcon(context: Context): Drawable {
        return ContextCompat.getDrawable(context, R.drawable.ic_launcher_foreground)!!
    }

    override fun getTitle(context: Context): String {
        return requireArguments().getString(KEY_TITLE)!!
    }
}