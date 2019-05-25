package com.paweloot.dodgeit

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context) :
    SurfaceView(context), SurfaceHolder.Callback {

    companion object {
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels

        const val LEVEL_PIZZA = 0
        const val LEVEL_BRICK = 1
    }

    private val pizzaBackgroundBitmap = BitmapFactory.decodeResource(resources, R.drawable.pizzeria)
    private val cookBitmap = BitmapFactory.decodeResource(resources, R.drawable.cook)
    private val builderBitmap = BitmapFactory.decodeResource(resources, R.drawable.builder)
    private val boyBitmap = BitmapFactory.decodeResource(resources, R.drawable.boy)
    private val pizzaBitmap = BitmapFactory.decodeResource(resources, R.drawable.pizza)
    private val brickBitmap = BitmapFactory.decodeResource(resources, R.drawable.brick)

    private val thread: MainThread = MainThread(holder, this)

    private val scorePanel = ScorePanel()
    private val cookSprite = CookSprite(cookBitmap, 0f, scorePanel.bottom + 5)
    private val builderSprite = BuilderSprite(builderBitmap, 0f, scorePanel.bottom + 5)
    private val boySprite = BoySprite(boyBitmap)
    private val fallingObjects = ArrayList<FallingObject>()

    private var currentLevel = LEVEL_PIZZA
    private var levelJustChanged = false
    private var totalScore: Int = 0

    private val dropInterval = 60
    private var nextDropCounter = 0

    init {
        holder.addCallback(this)
        isFocusable = true
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawColor(Color.BLACK)

        when (currentLevel) {
            LEVEL_PIZZA -> {
                canvas?.drawBitmap(pizzaBackgroundBitmap, 0f, 0f, null)
                cookSprite.draw(canvas)
            }
            LEVEL_BRICK -> {
                canvas?.drawColor(Color.WHITE)
                builderSprite.draw(canvas)
            }
        }

        scorePanel.draw(canvas)
        boySprite.draw(canvas)
        for (fo in fallingObjects) fo.draw(canvas)
    }

    fun update() {
        logic()

        when (currentLevel) {
            LEVEL_PIZZA -> {
//                logicForPizzaLevel()

                cookSprite.update()
            }
            LEVEL_BRICK -> {
//                logicForBrickLevel()

                builderSprite.update()
            }
        }

        boySprite.update()
        for (fo in fallingObjects) fo.update()

        if (totalScore != 0 && totalScore % 3 == 0 && !levelJustChanged) {
            changeLevel()
            levelJustChanged = true
        }

        if (totalScore % 3 == 1) levelJustChanged = false
    }

    private fun changeLevel() {
        when (currentLevel) {
            LEVEL_PIZZA -> currentLevel = LEVEL_BRICK
            LEVEL_BRICK -> currentLevel = LEVEL_PIZZA
        }

        fallingObjects.clear()
    }


    private fun logic() {
        checkForOverlapping()
        checkToDropNewObject()
    }

    private fun checkForOverlapping() {
        val boySpriteRect = RectF(
            boySprite.x, boySprite.y,
            boySprite.x + boySprite.width,
            boySprite.y + boySprite.height
        )
        var fallingObjectRect: RectF


        val toBeRemoved = ArrayList<FallingObject>()
        for (fo in fallingObjects) {
            fallingObjectRect = RectF(fo.x, fo.y, fo.x + fo.width, fo.y + fo.height)

            if (RectF.intersects(fallingObjectRect, boySpriteRect)) {
                toBeRemoved.add(fo)
                totalScore += 1
                scorePanel.update(totalScore)
            }
        }

        fallingObjects.removeAll(toBeRemoved)
    }

    private fun checkToDropNewObject() {
        nextDropCounter += 1

        if (timeToDropNewObject()) {
            when (currentLevel) {
                LEVEL_PIZZA -> dropNewPizza()
                LEVEL_BRICK -> dropNewBrick()
            }
        }
    }

    private fun timeToDropNewObject(): Boolean {
        if (nextDropCounter == dropInterval) {
            nextDropCounter = 0
            return true
        }

        return false
    }

    private fun dropNewPizza() {
        fallingObjects.add(FallingObject(pizzaBitmap, cookSprite.x, cookSprite.y + cookSprite.height))
    }

    private fun dropNewBrick() {
        fallingObjects.add(FallingObject(brickBitmap, builderSprite.x, builderSprite.y + builderSprite.height))
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

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, format: Int, width: Int, height: Int) {}
}
