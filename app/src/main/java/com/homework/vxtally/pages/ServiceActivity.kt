package com.homework.vxtally.pages

import android.content.Intent
import android.os.Bundle
import com.homework.vxtally.databinding.ActivityServiceBinding
import com.homework.vxtally.pages.bill.BillActivity
import com.ysw.android.extensions.viewbinding.BaseBindingActivity

class ServiceActivity : BaseBindingActivity<ActivityServiceBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(viewBinding) {
            outAndGet.setOnClickListener {
                startActivity(Intent(this@ServiceActivity, IOActivity::class.java))
            }
            toolbar.apply {
                setNavigationOnClickListener {
                    finishAfterTransition()
                }
                setOnMenuItemClickListener {
                    BillActivity.startBillActivity(this@ServiceActivity)
                    true
                }
            }
        }
    }

    override fun generateViewBinding(): ActivityServiceBinding {
        return ActivityServiceBinding.inflate(layoutInflater)
    }
}