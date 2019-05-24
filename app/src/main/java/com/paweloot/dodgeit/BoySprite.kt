package com.paweloot.dodgeit

import android.graphics.Bitmap
import com.paweloot.dodgeit.GameView.Companion.screenHeight
import com.paweloot.dodgeit.GameView.Companion.screenWidth

class BoySprite(private val image: Bitmap) : CharacterSprite(image) {

    private var xVelocity = 5f
    private var freezed = false

    init {
        this.x = screenWidth / 2f - (image.width / 2f)
        this.y = (screenHeight - image.height - 5).toFloat()
    }

    override fun update() {
        x += xVelocity

        if (x >= screenWidth - image.width || x <= 0) freeze()
    }

    fun changeDirection() {
        this.xVelocity *= -1
    }

    fun moveRight() {
        if (freezed) unfreeze()
        this.xVelocity = 5f
    }

    fun moveLeft() {
        if (freezed) unfreeze()
        this.xVelocity = -5f
    }

    private fun freeze() {
        freezed = true

        if (this.x >= screenWidth - image.width) {
            this.x = (screenWidth - image.width).toFloat()
        } else if (this.x <= 0) {
            this.x = 0f
        }
    }

    private fun unfreeze() {
        freezed = false
    }
}