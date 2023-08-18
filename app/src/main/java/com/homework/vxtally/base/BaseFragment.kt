package com.homework.vxtally.base

import androidx.viewbinding.ViewBinding
import com.ysw.android.extensions.viewbinding.BaseViewBindingFragment

abstract class BaseFragment<T : ViewBinding> : BaseViewBindingFragment<T>(), TabInfoProvider {

}