package com.homework.vxtally.pages.bill

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.homework.vxtally.R

class BillItemDecoration : ItemDecoration() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        style = Paint.Style.FILL
        strokeWidth = 0.5f
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        parent.children.forEach {
            val position = parent.getChildLayoutPosition(it)
            val viewBounds = Rect()
            parent.getDecoratedBoundsWithMargins(it, viewBounds)
            if (position != parent.adapter!!.itemCount - 1) {
                c.drawLine(
                    it.findViewById<View>(R.id.billName)!!.left.toFloat(),
                    viewBounds.bottom.toFloat(),
                    viewBounds.width().toFloat() - 20f,
                    viewBounds.bottom.toFloat(),
                    paint
                )
            }
        }
    }
}