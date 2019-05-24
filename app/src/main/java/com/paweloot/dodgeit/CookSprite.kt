package com.paweloot.dodgeit

import android.graphics.Bitmap
import com.paweloot.dodgeit.GameView.Companion.screenWidth

class CookSprite(private val image: Bitmap) : GameObject(image) {

    private var xVelocity = 5f

    init {
        this.x = screenWidth / 2f - (image.width / 2f)
        this.y = 5f
    }

    override fun update() {
        x += xVelocity

        if (x > screenWidth - image.width || x < 0) {
            xVelocity *= -1
        }
    }
}