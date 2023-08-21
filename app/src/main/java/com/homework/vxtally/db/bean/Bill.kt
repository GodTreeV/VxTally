package com.homework.vxtally.db.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ysw.android.extensions.currentTimeMillis

@Entity(tableName = "table_bill")
data class Bill(
    @PrimaryKey
    var billId: Long = currentTimeMillis,
    var title: String = "",
    var money: Long = 0L,
    var icon: String = "",
    var time: Long = currentTimeMillis,
    var isOut: Boolean = true
) {
    fun clear() {
        billId = currentTimeMillis
        title = ""
        money = 0L
        icon = ""
        time = currentTimeMillis
        isOut = true
    }
}
