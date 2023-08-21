package com.homework.vxtally.db.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.homework.vxtally.base.TimeHelper
import com.ysw.android.extensions.currentTimeMillis

@Entity(tableName = "table_bill")
data class Bill(
    @PrimaryKey
    var billId: Long = currentTimeMillis,
    var title: String = "",
    var money: Float = 0f,
    var icon: String = "",
    var time: Long = currentTimeMillis,
    var isOut: Boolean = true
) {
    fun clear() {
        billId = currentTimeMillis
        title = ""
        money = 0f
        icon = ""
        time = currentTimeMillis
        isOut = true
    }

    fun getYear(): Int {
        val s = TimeHelper.getFormatTimestamp(time).substring(0, 3)
        return s.toInt()
    }

    fun getMonth(): Int {
        val s = TimeHelper.getFormatTimestamp(time).substring(5, 7)
        return s.toInt()
    }
}
