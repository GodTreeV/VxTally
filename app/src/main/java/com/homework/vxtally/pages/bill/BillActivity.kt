package com.homework.vxtally.pages.bill

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.homework.vxtally.databinding.ActivityBillBinding
import com.homework.vxtally.db.BillDb
import com.homework.vxtally.db.bean.Bill
import com.ysw.android.extensions.logE
import com.ysw.android.extensions.mainLaunch
import com.ysw.android.extensions.viewbinding.BaseBindingActivity
import com.ysw.android.extensions.withIoContext

class BillActivity : BaseBindingActivity<ActivityBillBinding>() {

    companion object {
        fun startBillActivity(caller: Context) {
            caller.startActivity(
                Intent(caller, BillActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        }
    }

    @BillType
    var billTypeValue: Int = BillType.TYPE_ALL

    private lateinit var billAdapter: BillAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        billAdapter = BillAdapter()

        with(viewBinding) {
            billList.adapter = billAdapter

            toolbar.setNavigationOnClickListener {
                finishAndRemoveTask()
            }

            billType.setOnClickListener {
                TypeDialogFragment(this@BillActivity).apply {
                    show(supportFragmentManager, "")
                }
            }

            billStatistics.setOnClickListener {
                BillStatisticsActivity.startBillStatisticsActivity(this@BillActivity)
            }
        }
    }

    override fun generateViewBinding(): ActivityBillBinding {
        return ActivityBillBinding.inflate(layoutInflater)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateBillList() {
        mainLaunch {
            withIoContext {
                val dao = BillDb.getDao(this@BillActivity)
                val list = when (billTypeValue) {
                    BillType.TYPE_ALL -> dao.getAllBills()
                    BillType.TYPE_OUT -> dao.getOutBills()
                    BillType.TYPE_IN -> dao.getInBills()
                    else -> dao.getAllBills()
                }

                logE { "updateBillList : list = $list, type = $billTypeValue" }

                mainLaunch {
                    billAdapter.apply {
                        data.clear()
                        data.addAll(list)
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }
}