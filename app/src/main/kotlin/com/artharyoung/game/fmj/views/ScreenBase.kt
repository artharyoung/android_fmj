package com.artharyoung.game.fmj.views

import android.graphics.Canvas

abstract class ScreenBase {

  abstract fun update(delta: Long)

  abstract fun draw(canvas: Canvas)

  abstract fun onKeyDown(key: Int)

  abstract fun onKeyUp(key: Int)

  open fun isPopup(): Boolean {
    return false
  }

}