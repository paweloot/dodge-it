package com.paweloot.dodgeit

import android.graphics.Bitmap
import android.graphics.Canvas

abstract class GameObject(private val image: Bitmap) {

    var x: Float = 0f
    var y: Float = 0f

    fun getImageWidth() = image.width

    fun getImageHeight() = image.height

    fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(image, x, y, null)
    }

    abstract fun update()
}