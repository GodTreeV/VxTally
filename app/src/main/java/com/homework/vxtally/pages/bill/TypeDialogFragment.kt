package com.homework.vxtally.pages.bill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButtonToggleGroup
import com.homework.vxtally.R
import com.ysw.android.extensions.logE

class TypeDialogFragment(
    private val billActivity: BillActivity
) : BottomSheetDialogFragment() {

    private lateinit var toggleButtonGroup: MaterialButtonToggleGroup

    private var type = billActivity.billTypeValue

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toggleButtonGroup = view.findViewById(R.id.toggleButton)

        toggleButtonGroup.check(
            when (billActivity.billTypeValue) {
                BillType.TYPE_ALL -> R.id.button1
                BillType.TYPE_OUT -> R.id.button2
                BillType.TYPE_IN -> R.id.button3
                else -> R.id.button1
            }
        )
        toggleButtonGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                type = when (checkedId) {
                    R.id.button1 -> {
                        BillType.TYPE_ALL
                    }

                    R.id.button2 -> {
                        BillType.TYPE_OUT
                    }

                    R.id.button3 -> {
                        BillType.TYPE_IN
                    }

                    else -> {
                        BillType.TYPE_ALL
                    }
                }

                logE { "addOnButtonCheckedListener : type = $type" }
            }
        }

        view.findViewById<View>(R.id.cancelBt).setOnClickListener {
            dismiss()
            type = BillType.TYPE_ALL
        }

        view.findViewById<View>(R.id.confirm).setOnClickListener {
            billActivity.billTypeValue = type
            logE { "confirm : ${billActivity.billTypeValue}" }
            dismiss()
            billActivity.updateBillList()
        }
    }
}