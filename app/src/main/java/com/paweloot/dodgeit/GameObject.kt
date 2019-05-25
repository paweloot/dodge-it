package com.paweloot.dodgeit

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF

abstract class GameObject(protected val image: Bitmap, var x: Float, var y: Float) {
    val rect = RectF(x, y, x + image.width, y + image.height)

    val width = image.width
    val height = image.height

    fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(image, x, y, null)
    }

    abstract fun update()
}