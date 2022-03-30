package com.diegopizzo.display_cutouts_demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View


class CutoutsView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    var rectList: MutableList<Rect>? = null

    private val rectPaint =
        Paint().apply {
            isAntiAlias = true
            color = Color.WHITE
            style = Paint.Style.STROKE
            strokeWidth = 2F
        }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        rectList?.let {
            it.map { rect ->
                canvas?.drawRect(rect, rectPaint)
            }
        }
    }
}