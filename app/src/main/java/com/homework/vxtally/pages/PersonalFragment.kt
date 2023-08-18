package com.homework.vxtally.pages

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.homework.vxtally.R
import com.homework.vxtally.base.BaseFragment
import com.homework.vxtally.base.KEY_TITLE
import com.homework.vxtally.databinding.FgPersonalBinding

class PersonalFragment : BaseFragment<FgPersonalBinding>() {
    override var layoutRes: Int = R.layout.fg_personal

    override fun getIcon(context: Context): Drawable {
        return ContextCompat.getDrawable(context, R.drawable.ic_launcher_foreground)!!
    }

    override fun getTitle(context: Context): String {
        return requireArguments().getString(KEY_TITLE)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            service.setOnClickListener {
                requireContext().startActivity(Intent(requireContext(), ServiceActivity::class.java))
            }
        }
    }
}