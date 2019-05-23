package com.paweloot.dodgeit

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas

class CharacterSprite(private val image: Bitmap) {

    private var x: Float = 50f
    private val y: Float = 50f
    private var xVelocity = 10

    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels

    fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(image, x, y, null)
    }

    fun update() {
        x += xVelocity

        if (x > screenWidth - image.width || x < 0) {
            xVelocity *= -1
        }
    }
}