package com.paweloot.dodgeit

import android.graphics.Canvas
import android.view.SurfaceHolder
import java.lang.Exception

class MainThread(
    private val surfaceHolder: SurfaceHolder,
    private val gameView: GameView
) : Thread() {

    companion object {
        var canvas: Canvas? = null
    }

    private var targetFPS = 60
    private var averageFPS = 0

    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var waitTime: Long = 0
    private var totalTime: Long = 0
    private var frameCount = 0
    private var targetTime = 1000 / targetFPS

    private var running: Boolean = false

    fun setRunning(isRunning: Boolean) {
        running = isRunning
    }

    override fun run() {
        while (running) {
            startTime = System.nanoTime()
            canvas = null

            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    gameView.update()
                    gameView.draw(canvas)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            elapsedTime = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - elapsedTime

            if (waitTime > 0) {
                try {
                    sleep(waitTime)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


            totalTime += System.nanoTime() - startTime
            frameCount++
            if (frameCount == targetFPS) {
                averageFPS = (1000 / ((totalTime / frameCount) / 1000000)).toInt()
                frameCount = 0
                totalTime = 0
                System.out.println("FPS: $averageFPS")
            }
        }
    }
}