package com.homework.vxtally.pages.bill

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.ReceiverCallNotAllowedException
import android.os.Bundle
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.homework.vxtally.R
import com.homework.vxtally.base.DateAndTimePickers
import com.homework.vxtally.base.TimeHelper
import com.homework.vxtally.databinding.ActivityBillStatisticsBinding
import com.homework.vxtally.db.BillDb
import com.homework.vxtally.db.bean.Bill
import com.ysw.android.extensions.logE
import com.ysw.android.extensions.mainLaunch
import com.ysw.android.extensions.viewbinding.BaseBindingActivity
import com.ysw.android.extensions.withIoContext
import java.util.Calendar
import java.util.Date
import kotlin.properties.Delegates

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

    private var currentBillType by Delegates.observable(BillType.TYPE_OUT) { _, old, new ->
        updateBarChat(new)
        updateTextInfo(currentSelectedYear, currentSelectedMonth)
        updateRv()
    }

    private var currentSelectedYear = 0
    private var currentSelectedMonth = 0

    private lateinit var rankAdapter: RankAdapter

    private suspend fun getFilterBillList(year: Int, month: Int): List<Bill> {
        val range = TimeHelper.getMonthTimeRange(year, month)
        return withIoContext {
            BillDb.getDao(this@BillStatisticsActivity).getBillsByTimeRange(range.lower, range.upper)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val calendar = Calendar.getInstance()

        with(viewBinding) {
            rankAdapter = RankAdapter()
            rv.adapter = rankAdapter

            toolbar.setNavigationOnClickListener {
                finishAndRemoveTask()
            }

            toggleButtonGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
                if (isChecked) {
                    when (checkedId) {
                        R.id.out -> {
                            currentBillType = BillType.TYPE_OUT
                        }

                        R.id.`in` -> {
                            currentBillType = BillType.TYPE_IN
                        }

                        else -> {
                            // all
                        }
                    }
                }
            }

            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH] + 1

            timeSelector.setOnClickListener {
                DateAndTimePickers.showDatePickerDialog(this@BillStatisticsActivity) {
                    val c = Calendar.getInstance()
                    c.time = Date(it)
                    val pickedYear = c[Calendar.YEAR]
                    val pickerMonth = c[Calendar.MONTH] + 1
                    updateTimeSelectorText(pickedYear, pickerMonth)
                    updateTextInfo(pickedYear, pickerMonth)
                    updateBarChat(currentBillType)
                    updateRv()
                }
            }

            updateTimeSelectorText(year, month)
            updateTextInfo(year, month)
            updateBarChat(currentBillType)

            updateRv()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateTimeSelectorText(year: Int, month: Int) {
        viewBinding.timeSelector.text = "${year}年${month}月"
        currentSelectedYear = year
        currentSelectedMonth = month
    }

    @SuppressLint("SetTextI18n")
    private fun updateTextInfo(year: Int, month: Int) {
        mainLaunch {
            withIoContext {
                val billRangeList = getFilterBillList(year, month)

                logE { "updateTextInfo: $billRangeList" }

                val billSize = billRangeList.size

                val outSize = billRangeList.filter { it.isOut }.size
                val inSize = billSize - outSize

                val moneySum =
                    billRangeList.filter { if (currentBillType == BillType.TYPE_OUT) it.isOut else !it.isOut }
                        .sumOf { it.money.toDouble() }
                mainLaunch {
                    viewBinding.billCountTv.text = getString(
                        R.string.bill_counts,
                        if (currentBillType == BillType.TYPE_OUT) "支出" else "收入",
                        "${if (currentBillType == BillType.TYPE_OUT) outSize else inSize}"
                    )

                    viewBinding.billMoneySumTv.text = "¥ ${moneySum}"
                }
            }
        }
    }

    private fun updateBarChat(@BillType billType: Int) {
        with(viewBinding) {
            mainLaunch {
                val billList = withIoContext {
                    if (billType != BillType.TYPE_OUT) {
                        BillDb.getDao(this@BillStatisticsActivity).getOutBills()
                    } else {
                        BillDb.getDao(this@BillStatisticsActivity).getInBills()
                    }
                }

                logE { "asdasdsad $billList" }

                val billMap = mutableMapOf<Int, Float>()

                billList.forEach {
                    if (!billMap.keys.contains(it.getMonth())) {
                        billMap[it.getMonth()] = 0f
                    }
                }

                val finalMBillMap = mutableMapOf<Int, Float>()
                billMap.keys.forEach {
                    finalMBillMap[it] =
                        billList.filter { b -> b.getMonth() == it }.sumOf { it.money.toDouble() }
                            .toFloat()
                }

                finalMBillMap.forEach { t, u ->
                    logE { "月 = $t, 总计 = $u" }
                }

                mainLaunch {
                    val barEntries = mutableListOf<BarEntry>()


                    finalMBillMap.forEach { (t, u) ->
                        barEntries.add(BarEntry(t.toFloat(), u))
                    }

                    barChart.description.text = ""

                    barChart.xAxis.apply {
                        setDrawGridLines(false)
                        position = XAxis.XAxisPosition.BOTTOM
                        valueFormatter = object : ValueFormatter() {
                            override fun getFormattedValue(value: Float): String {
                                return "${value.toInt()}月"
                            }
                        }
                    }

                    val barDataSet = BarDataSet(
                        barEntries,
                        "账单(${if (currentBillType == BillType.TYPE_OUT) "支出" else "收入"})"
                    )
                    barChart.data = BarData(barDataSet)

                    barChart.invalidate()
                }
            }
        }
    }

    override fun generateViewBinding(): ActivityBillStatisticsBinding {
        return ActivityBillStatisticsBinding.inflate(layoutInflater)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateRv() {
        mainLaunch {
            withIoContext {
                val billRangeList = getFilterBillList(currentSelectedYear, currentSelectedMonth)
                mainLaunch {

                    viewBinding.rvTitle.text = "本月${if (currentBillType == BillType.TYPE_OUT) "支出" else "收入"}排行榜"

                    rankAdapter.apply {
                        data.clear()
                        data.addAll(billRangeList.filter { if (currentBillType == BillType.TYPE_OUT) it.isOut else !it.isOut }
                            .sortedByDescending { it.money })
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }
}