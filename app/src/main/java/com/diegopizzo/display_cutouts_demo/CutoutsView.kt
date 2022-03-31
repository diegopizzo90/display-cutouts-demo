package com.diegopizzo.display_cutouts_demo

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class CutoutsView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    var rectList: MutableList<Rect>? = null
        set(value) {
            field = value
            invalidate()
        }

    private val redPaint =
        Paint().apply {
            isAntiAlias = true
            color = Color.YELLOW
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = 15F
        }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        rectList?.let {
            it.map { rect ->
                canvas?.clipOutRect(rect)
                canvas?.drawRect(rect, redPaint)
            }
            startFlashingAnimation()
        }
    }

    private fun startFlashingAnimation() {
        val objAnimator = ObjectAnimator.ofFloat(this, ALPHA, 0f, 1f)
        objAnimator.duration = 800
        objAnimator.repeatMode = ValueAnimator.REVERSE
        objAnimator.repeatCount = ValueAnimator.INFINITE
        objAnimator.start()
    }
}