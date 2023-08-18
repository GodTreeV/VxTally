package com.homework.vxtally.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.homework.vxtally.db.bean.Bill

@Database(
    entities = [Bill::class],
    exportSchema = false,
    version = 1,
)
abstract class BillDb : RoomDatabase() {
    companion object {
        private var instance: BillDb? = null

        fun getInstance(context: Context): BillDb {
            return synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BillDb::class.java,
                        "BillDatabase"
                    ).build()
                    instance!!
                } else instance!!
            }
        }

        fun getDao(context: Context) = getInstance(context).dao()
    }

    abstract fun dao(): BillDao
}