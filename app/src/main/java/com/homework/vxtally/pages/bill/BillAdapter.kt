package com.homework.vxtally.pages.bill

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.homework.vxtally.BR
import com.homework.vxtally.R
import com.homework.vxtally.databinding.BillBinding
import com.homework.vxtally.db.BillDb
import com.homework.vxtally.db.bean.Bill
import com.ysw.android.extensions.databinding.AbsBindingRVAdapter
import com.ysw.android.extensions.mainLaunch
import com.ysw.android.extensions.withIoContext

class BillAdapter : AbsBindingRVAdapter<BillBinding, Bill>() {
    override val data: MutableList<Bill> = mutableListOf()

    override fun getLayoutRes(): Int  = R.layout.bill

    override fun getVariableId(): Int  = BR.bill

    @SuppressLint("NotifyDataSetChanged")
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(BillItemDecoration())
        mainLaunch {
            withIoContext {
                data.clear()
                data.addAll(BillDb.getDao(recyclerView.context).getAllBills())
                mainLaunch {
                    notifyDataSetChanged()
                }
            }
        }
    }
}