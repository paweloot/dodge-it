package com.paweloot.dodgeit

import android.graphics.Bitmap
import com.paweloot.dodgeit.GameView.Companion.screenWidth

class BuilderSprite(image: Bitmap, x: Float, y: Float) : GameObject(image, x, y) {
    private var xVelocity = 5f

    override fun update() {
        x += xVelocity

        if (x > screenWidth - image.width || x < 0) {
            xVelocity *= -1
        }
    }
}