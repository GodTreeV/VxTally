package com.homework.vxtally.pages

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.homework.vxtally.R
import com.homework.vxtally.base.BaseFragment
import com.homework.vxtally.base.KEY_IO_TYPE
import com.homework.vxtally.databinding.FgIoBinding
import com.homework.vxtally.utils.Fragments
import com.ysw.android.extensions.viewbinding.BaseViewBindingFragment

class IOFragment : BaseFragment<FgIoBinding>() {

    companion object {
        const val TYPE_OUT = 0
        const val TYPE_IN = 1

        fun newInstance(type: Int) = Fragments.newInstance(IOFragment::class.java) {
            it.putInt(KEY_IO_TYPE, type)
        }
    }

    override var layoutRes: Int
        get() = R.layout.fg_io
        set(value) {}

    private val type by lazy { arguments?.getInt(KEY_IO_TYPE) ?: TYPE_OUT }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getIcon(context: Context): Drawable {
        return ContextCompat.getDrawable(context, R.drawable.ic_launcher_foreground)!!
    }

    override fun getTitle(context: Context): String {
        return if (requireArguments().getInt(KEY_IO_TYPE) == TYPE_OUT) "支出" else "收入"
    }
}