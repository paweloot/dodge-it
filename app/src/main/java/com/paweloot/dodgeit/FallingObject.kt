package com.paweloot.dodgeit

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.paweloot.dodgeit.GameView.Companion.screenWidth
import kotlin.random.Random

class FallingObject(private val image: Bitmap) : GameObject(image) {

    private val yVelocity = 5f

    init {
        this.x = Random.nextInt(screenWidth - image.width).toFloat()
        this.y = 111f
    }

    override fun update() {
        this.y += yVelocity
    }
}