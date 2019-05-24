package com.paweloot.dodgeit

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.hardware.SensorEventListener
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context) :
    SurfaceView(context), SurfaceHolder.Callback {

    companion object {
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    }

    private val cookBitmap = BitmapFactory.decodeResource(resources, R.drawable.cook)
    private val boyBitmap = BitmapFactory.decodeResource(resources, R.drawable.boy)
    private val pizzaBitmap = BitmapFactory.decodeResource(resources, R.drawable.pizza)

    private val thread: MainThread = MainThread(holder, this)

    private val scorePanel = ScorePanel()
    private val cookSprite = CookSprite(cookBitmap)
    private val boySprite = BoySprite(boyBitmap)
    private val fallingObjects = ArrayList<FallingObject>()

    private var totalScore: Int = 0

    init {
        holder.addCallback(this)
        isFocusable = true

        this.fallingObjects.add(FallingObject(pizzaBitmap))
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas?.drawColor(Color.WHITE)
        scorePanel.draw(canvas)

        cookSprite.draw(canvas)
        boySprite.draw(canvas)
        for (fallingObject in fallingObjects) {
            fallingObject.draw(canvas)
        }
    }

    fun update() {
        cookSprite.update()
        boySprite.update()
        for (fallingObject in fallingObjects) {
            fallingObject.update()
        }

        checkForOverlapping()
        checkToDropNewObject()
    }

    private fun checkForOverlapping() {
        val boySpriteRect = RectF(
            boySprite.x, boySprite.y,
            boySprite.x + boySprite.getImageWidth(),
            boySprite.y + boySprite.getImageHeight()
        )
        var fallingObjectRect: RectF


        for (fo in fallingObjects) {
            fallingObjectRect = RectF(
                fo.x, fo.y,
                fo.x + fo.getImageWidth(), fo.y + fo.getImageHeight()
            )

            if (RectF.intersects(fallingObjectRect, boySpriteRect)) {
                fallingObjects.remove(fo)
                totalScore += 1
                scorePanel.update(totalScore)
            }
        }
    }


    private fun checkToDropNewObject() {
        if (fallingObjects.last().y > (screenHeight / 2)) {
            fallingObjects.add(FallingObject(pizzaBitmap))
        }
    }

    fun deviceTiltedRight() {
        boySprite.moveRight()
    }

    fun deviceTiltedLeft() {
        boySprite.moveLeft()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true

        while (retry) {
            try {
                thread.setRunning(false)
                thread.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            retry = false
        }
    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }
}
