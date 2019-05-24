package com.paweloot.dodgeit

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.hardware.SensorEventListener
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context) :
    SurfaceView(context), SurfaceHolder.Callback {

    companion object {
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    }

    private val thread: MainThread = MainThread(holder, this)

    private val cookSprite: CookSprite = CookSprite(BitmapFactory.decodeResource(resources, R.drawable.cook))
    private val boySprite: BoySprite = BoySprite(BitmapFactory.decodeResource(resources, R.drawable.boy))

    init {
        holder.addCallback(this)
        isFocusable = true
    }

    fun update() {
        cookSprite.update()
        boySprite.update()
    }

    fun deviceTiltedRight() {
        boySprite.moveRight()
    }

    fun deviceTiltedLeft() {
        boySprite.moveLeft()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas?.drawColor(Color.WHITE)

        cookSprite.draw(canvas)
        boySprite.draw(canvas)
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
