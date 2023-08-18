package com.homework.vxtally.pages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.homework.vxtally.R
import com.homework.vxtally.databinding.ActivityServiceBinding
import com.ysw.android.extensions.viewbinding.BaseBindingActivity

class ServiceActivity : BaseBindingActivity<ActivityServiceBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(viewBinding) {
            outAndGet.setOnClickListener {
                startActivity(Intent(this@ServiceActivity, IOActivity::class.java))
            }
        }
    }

    override fun generateViewBinding(): ActivityServiceBinding {
        return ActivityServiceBinding.inflate(layoutInflater)
    }
}