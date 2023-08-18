package com.homework.vxtally.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.homework.vxtally.R
import com.homework.vxtally.base.KEY_TITLE
import com.homework.vxtally.base.TabInfoProvider
import com.homework.vxtally.databinding.ActivityIoactivityBinding
import com.homework.vxtally.utils.Fragments
import com.ysw.android.extensions.cast
import com.ysw.android.extensions.viewbinding.BaseBindingActivity

class IOActivity : BaseBindingActivity<ActivityIoactivityBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(viewBinding) {

            vp2.apply {
                adapter = VpAdapter()
                offscreenPageLimit = 2
            }

            TabLayoutMediator(
                navigation,
                vp2
            ) { tab, position ->
                tab.apply {
                    icon = vp2.adapter!!.cast<VpAdapter>().fragments[position].cast<TabInfoProvider>().getIcon(this@IOActivity)
                    text = vp2.adapter!!.cast<VpAdapter>().fragments[position].cast<TabInfoProvider>().getTitle(this@IOActivity)
                }
            }.attach()
        }
    }

    override fun generateViewBinding(): ActivityIoactivityBinding {
        return ActivityIoactivityBinding.inflate(layoutInflater)
    }

    private inner class VpAdapter : FragmentStateAdapter(this) {
        val fragments = mutableListOf<Fragment>()

        init {
            fragments.addAll(
                arrayOf(
                    IOFragment.newInstance(IOFragment.TYPE_OUT),
                    IOFragment.newInstance(IOFragment.TYPE_IN)
                ).toMutableList()
            )
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }
}