package com.ferum_bot.cryptocharts.ui.recycler

import android.graphics.Rect
import android.view.View
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView
import com.ferum_bot.cryptocharts.core.extensions.dpToPx

class MarginDecorator(
    @Dimension(unit = Dimension.DP)
    private val leftMargin: Int = 0,

    @Dimension(unit = Dimension.DP)
    private val rightMargin: Int = 0,

    @Dimension(unit = Dimension.DP)
    private val topMargin: Int = 0,

    @Dimension(unit = Dimension.DP)
    private val bottomMargin: Int = 0,

    @Dimension(unit = Dimension.DP)
    private val spaceBetweenItems: Int = 0,
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = view.context.dpToPx(leftMargin)
        outRect.right = view.context.dpToPx(rightMargin)

        val position = parent.getChildAdapterPosition(view)
        val itemsCount = parent.adapter?.itemCount ?: return
        if (position == RecyclerView.NO_POSITION) {
            return
        }

        if (position == 0) {
            outRect.top = view.context.dpToPx(topMargin)
            return
        }
        if (position == itemsCount - 1) {
            outRect.bottom = view.context.dpToPx(bottomMargin)
            return
        }
        outRect.bottom = view.context.dpToPx(spaceBetweenItems)
    }

}