package com.homework.vxtally.pages

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.homework.vxtally.R
import com.homework.vxtally.base.APP_ROOT_FILE_PATH
import com.homework.vxtally.base.BaseFragment
import com.homework.vxtally.base.DateAndTimePickers
import com.homework.vxtally.databinding.FgIoBinding
import com.homework.vxtally.db.BillDb
import com.homework.vxtally.db.bean.Bill
import com.ysw.android.extensions.currentTimeMillis
import com.ysw.android.extensions.logE
import com.ysw.android.extensions.mainLaunch
import com.ysw.android.extensions.smartCreate
import com.ysw.android.extensions.toast
import com.ysw.android.extensions.withIoContext
import java.io.File
import java.io.FileOutputStream

class IOFragment : BaseFragment<FgIoBinding>() {

    override var layoutRes: Int
        get() = R.layout.fg_io
        set(value) {}

    private lateinit var pickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>

    private val newBill = Bill()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pickerLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            it?.let {
                binding.icon.setImageURI(it)
                updateBillIconFilePath(it) {
                    newBill.icon = it.absolutePath
                }
            } ?: run {
                requireContext().toast("uri is null")
            }
        }
    }

    private fun updateBillIconFilePath(fileUri: Uri, cb: (File) -> Unit) {
        val file = File(APP_ROOT_FILE_PATH, "icon_$currentTimeMillis.jpg").smartCreate()
        requireContext().contentResolver.openInputStream(fileUri)?.use {
            it.copyTo(FileOutputStream(file))
            cb(file)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
                newBill.isOut = checkedId == R.id.button1
            }

            icon.setOnClickListener {
                pickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }

            addTime.setOnClickListener {
                DateAndTimePickers.showDatePickerDialog(requireActivity()) {
                    DateAndTimePickers.showTimePickerDialog(requireActivity()) { h, m ->
                        val pickedTime = DateAndTimePickers.getTimestamp(
                            DateAndTimePickers.getFormatTimestamp(it)
                                .substringBefore(" ") + " $h:$m:00"
                        )
                        requireContext().toast(DateAndTimePickers.getFormatTimestamp(pickedTime))
                        newBill.time = pickedTime
                    }
                }
            }

            commit.setOnClickListener {
                val nameStr = addName.editText?.text?.toString() ?: ""
                val moneyStr = addMoney.editText?.text?.toString() ?: ""
                if (nameStr.isEmpty() || moneyStr.isEmpty()) {
                    requireContext().toast("信息不能为空")
                    return@setOnClickListener
                }

                newBill.title = nameStr
                newBill.money = moneyStr.toLong()

                logE { "Commit to DB: bill = $newBill" }

                mainLaunch {
                    withIoContext {
                        BillDb.getDao(requireContext()).addBill(newBill)
                    }
                    requireContext().toast("添加成功")

                    newBill.clear()
                }
            }
        }
    }

    override fun getIcon(context: Context): Drawable {
        return ContextCompat.getDrawable(context, R.drawable.ic_launcher_foreground)!!
    }

    override fun getTitle(context: Context): String {
        return ""
    }
}