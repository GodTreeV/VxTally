package com.homework.vxtally.db.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ysw.android.extensions.currentTimeMillis

@Entity(tableName = "table_bill")
data class Bill(
    @PrimaryKey
    val billId: Long,
    var title: String = "",
    var icon: String = "",
    var time: Long = currentTimeMillis,
    var isOut: Boolean = true
)
