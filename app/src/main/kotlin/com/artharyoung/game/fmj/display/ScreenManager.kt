package com.artharyoung.game.fmj.display

import com.artharyoung.game.fmj.views.BaseScreen
import com.artharyoung.game.fmj.views.ScreenAnimation
import com.artharyoung.game.fmj.views.ScreenBase
import com.artharyoung.game.fmj.views.TestScreen
import java.util.*

object ScreenManager {
  val stack by lazy { Stack<BaseScreen>() }

  init {
    stack.push(ScreenAnimation(248))
  }
}