package com.artharyoung.game.fmj

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

class FmjApp : Application() {

  companion object {
    var instance: FmjApp by Delegates.notNull()
  }

  override fun attachBaseContext(base: Context?) {
    super.attachBaseContext(base)
    instance = this
  }
}