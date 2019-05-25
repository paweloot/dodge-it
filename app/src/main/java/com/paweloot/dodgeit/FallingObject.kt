package com.paweloot.dodgeit

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.paweloot.dodgeit.GameView.Companion.screenWidth
import kotlin.random.Random

class FallingObject(image: Bitmap, x: Float, y: Float) : GameObject(image, x, y) {
    companion object {
        var velocity = 7.5f
    }

    override fun update() {
        this.y += velocity
    }
}