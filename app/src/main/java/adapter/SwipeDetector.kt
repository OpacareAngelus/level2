package adapter

import android.view.MotionEvent
import kotlin.math.atan2
import kotlin.math.sqrt

abstract class SwipeDetector(minTouchLin: Int) {
    private var isStarted = false
    private var startX = 0f
    private var startY = 0f
    private var minTouchLin = 10

    init {
        this.minTouchLin = minTouchLin * 5
    }

    abstract fun onSwipeDetected(direction: Direction?)
    fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
                isStarted = true
            }
            MotionEvent.ACTION_MOVE -> {}
            MotionEvent.ACTION_UP -> {
                val dx = event.x - startX
                val dy = event.y - startY
                if (calcDist(dx, dy) >= minTouchLin) {
                    onSwipeDetected(Direction[calcAngle(dx, dy)])
                    startX = 0f
                    startY = startX
                    isStarted = false
                }
            }
            else -> {
                run {
                    startX = 0f
                    startY = startX
                }
                isStarted = false
            }
        }
        return false
    }

    //    private Direction getDirection(float dx, float dy) {
    //        return Direction.get(calcAngle(dx, dy));
    //    }
    private fun calcAngle(dx: Float, dy: Float): Int {
        return ((atan2(
            dy.toDouble(),
            dx.toDouble()
        ) + Math.PI) * 180 / Math.PI + 180).toInt() % 360
    }

    private fun calcDist(dx: Float, dy: Float): Double {
        return sqrt((dx * dx + dy * dy).toDouble())
    }

    enum class Direction {
        UN_EXPT, LEFT;

        companion object {
            operator fun get(angle: Int): Direction {
                var res = UN_EXPT
                if (inRange(angle, 315, 360) || inRange(angle, 0, 45)) res = LEFT
                return res
            }
        }
    }

    companion object {
        private fun inRange(angle: Int, min: Int, max: Int): Boolean {
            return angle in min..max
        }
    }
}