package com.paweloot.dodgeit

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.paweloot.dodgeit.GameView.Companion.screenWidth

class ScorePanel {
    private val rect = RectF(0f, 0f, screenWidth.toFloat(), 100f)

    val left = rect.left
    val top = rect.top
    val right = rect.right
    val bottom = rect.bottom

    var score = 0

    fun draw(canvas: Canvas?) {
        canvas?.drawRect(rect, Paint(Color.BLACK))

        val textPaint = Paint()
        textPaint.color = Color.WHITE
        textPaint.textSize = 80f
        canvas?.drawText("Score: $score", 10f, 80f, textPaint)
    }

    fun update(newScore: Int) {
        this.score = newScore
    }
}