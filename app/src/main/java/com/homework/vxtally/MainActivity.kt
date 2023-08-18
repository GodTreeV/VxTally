package com.homework.vxtally

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.tabs.TabLayoutMediator
import com.homework.vxtally.base.KEY_TITLE
import com.homework.vxtally.base.TabInfoProvider
import com.homework.vxtally.databinding.ActivityMainBinding
import com.homework.vxtally.pages.EmptyFragment
import com.homework.vxtally.pages.PersonalFragment
import com.homework.vxtally.utils.Fragments
import com.ysw.android.extensions.cast
import com.ysw.android.extensions.logE
import com.ysw.android.extensions.viewbinding.BaseBindingActivity

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    private lateinit var vpAdapter: VpAdapter

    override fun generateViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vpAdapter = VpAdapter()
        with(viewBinding) {
            TabLayoutMediator(
                tabLayout,
                vp2.apply {
                    adapter = vpAdapter
                    offscreenPageLimit = vpAdapter.itemCount
                }
            ) { tab, position ->
                logE { "$position" }
                vpAdapter.fragments[position].takeIf { it is TabInfoProvider }?.let {
                    if (position == vpAdapter.itemCount - 1) {
                        vp2.post {
                            BadgeUtils.attachBadgeDrawable(
                                BadgeDrawable.create(this@MainActivity).apply {
                                    this.backgroundColor = Color.RED
                                }, tab.view.children.first { it is ImageView }
                            )
                        }
                    }
                    it as TabInfoProvider
                    tab.apply {
                        icon = it.getIcon(this@MainActivity)
                        text = it.getTitle(this@MainActivity)
                    }
                }
            }.attach()
        }
    }

    private inner class VpAdapter : FragmentStateAdapter(this) {
        val fragments = mutableListOf<Fragment>()

        init {
            fragments.addAll(
                arrayOf(
                    Fragments.newInstance(EmptyFragment::class.java) {
                        it.putString(
                            KEY_TITLE,
                            "vx"
                        )
                    },
                    Fragments.newInstance(EmptyFragment::class.java) {
                        it.putString(
                            KEY_TITLE,
                            "vx"
                        )
                    },
                    Fragments.newInstance(EmptyFragment::class.java) {
                        it.putString(
                            KEY_TITLE,
                            "vx"
                        )
                    },
                    Fragments.newInstance(PersonalFragment::class.java) {
                        it.putString(
                            KEY_TITLE,
                            "vx"
                        )
                    },
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