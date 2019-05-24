package com.paweloot.dodgeit

import android.graphics.Bitmap
import android.graphics.Canvas

abstract class CharacterSprite(private val image: Bitmap) {

    protected var x: Float = 0f
    protected var y: Float = 0f

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(image, x, y, null)
    }

    abstract fun update()
}