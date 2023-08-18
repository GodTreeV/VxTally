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

    @Insert
    suspend fun addBill(bill: Bill)

    @Delete
    suspend fun deleteBill(bill: Bill)

    @Update
    suspend fun updateBill(bill: Bill)

    @Query("SELECT * FROM table_bill")
    fun getAllBillsAsFlow(): Flow<List<Bill>>
}