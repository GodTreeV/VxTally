package com.homework.vxtally.pages.bill

import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Range
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.homework.vxtally.databinding.ActivityBillStatisticsBinding
import com.ysw.android.extensions.currentTimeMillis
import com.ysw.android.extensions.logE
import com.ysw.android.extensions.mainLaunch
import com.ysw.android.extensions.viewbinding.BaseBindingActivity
import com.ysw.android.extensions.withIoContext
import java.time.Month
import java.util.Date

class BillStatisticsActivity : BaseBindingActivity<ActivityBillStatisticsBinding>() {

    companion object {
        fun startBillStatisticsActivity(caller: Context) {
            caller.startActivity(
                Intent(caller, BillStatisticsActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getTimeRangeByYearAndMonth(2023, 1)

        with(viewBinding) {

            mainLaunch {
                withIoContext {
                }
            }

            val barEntries = mutableListOf<BarEntry>()

            val barDataSet = BarDataSet(barEntries, "bill")
            barChart.xAxis.apply {
                setDrawGridLines(false)
            }
            barChart.data = BarData(barDataSet)
        }
    }

    override fun generateViewBinding(): ActivityBillStatisticsBinding {
        return ActivityBillStatisticsBinding.inflate(layoutInflater)
    }

    private fun getTimeRangeByYearAndMonth(year: Int, month: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, 1)
        calendar.roll(Calendar.DATE, -1)
        logE { "${calendar.get(Calendar.DATE)}" }
    }
}