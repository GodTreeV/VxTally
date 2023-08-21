package com.homework.vxtally.pages.bill

import com.homework.vxtally.BR
import com.homework.vxtally.R
import com.homework.vxtally.databinding.RankBinding
import com.homework.vxtally.db.bean.Bill
import com.ysw.android.extensions.databinding.AbsBindingRVAdapter

class RankAdapter : AbsBindingRVAdapter<RankBinding, Bill>() {
    override val data: MutableList<Bill> = mutableListOf()

    override fun getLayoutRes(): Int {
        return R.layout.rank
    }

    override fun getVariableId(): Int {
        return BR.bill
    }

    override fun bindDefaultData(holder: BindingViewHolder<RankBinding>, position: Int) {
        super.bindDefaultData(holder, position)
        holder.binding.rank.text = "${position + 1}"
    }
}