package com.homework.vxtally.pages

import com.homework.vxtally.databinding.ActivityIoactivityBinding
import com.ysw.android.extensions.viewbinding.BaseBindingActivity

class IOActivity : BaseBindingActivity<ActivityIoactivityBinding>() {
    override fun generateViewBinding(): ActivityIoactivityBinding {
        return ActivityIoactivityBinding.inflate(layoutInflater)
    }
}