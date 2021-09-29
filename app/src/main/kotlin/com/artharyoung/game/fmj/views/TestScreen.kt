package com.artharyoung.game.fmj.views

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.artharyoung.game.fmj.FmjApp
import com.artharyoung.game.fmj.R


class TestScreen : ScreenBase() {

  private val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

  override fun update(delta: Long) {

  }

  override fun draw(canvas: Canvas) {
    paint.color = ContextCompat.getColor(FmjApp.instance,R.color.black)
    paint.style = Paint.Style.FILL

    canvas.drawRect(0f, 0f, 100f, 100f, paint)

  }

  override fun onKeyDown(key: Int) {

  }

  override fun onKeyUp(key: Int) {

  }
}