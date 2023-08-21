package com.homework.vxtally.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.homework.vxtally.db.bean.Bill
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {
    @Query("SELECT * FROM table_bill")
    suspend fun getAllBills(): List<Bill>

    @Query("SELECT * FROM table_bill WHERE isOut == 0")
    suspend fun getOutBills(): List<Bill>

    @Query("SELECT * FROM table_bill WHERE isOut == 1")
    suspend fun getInBills(): List<Bill>

    @Query("SELECT * FROM TABLE_BILL WHERE time >= :lower AND time < :upper")
    suspend fun getBillsByTimeRange(lower: Long, upper: Long): List<Bill>

    @Insert
    suspend fun addBill(bill: Bill)

    @Delete
    suspend fun deleteBill(bill: Bill)

    @Update
    suspend fun updateBill(bill: Bill)

    @Query("SELECT * FROM table_bill")
    fun getAllBillsAsFlow(): Flow<List<Bill>>
}