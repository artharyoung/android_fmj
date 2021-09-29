package com.artharyoung.game.fmj.display

import android.content.Context
import android.graphics.SurfaceTexture
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.TextureView
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.artharyoung.game.fmj.GAME_TIME_LOOP
import com.artharyoung.game.fmj.LogUtil
import com.artharyoung.game.fmj.databinding.LayoutDisplayBinding
import kotlinx.coroutines.*

class DisplayView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), DefaultLifecycleObserver {

  private val binding by lazy {
    LayoutDisplayBinding.inflate(LayoutInflater.from(getContext()), this, true)
  }

  private lateinit var owner: LifecycleOwner
  private var drawJob: Job? = null

  init {
    binding.texture.apply {
      //设置背景透明
      isOpaque = false

      //设置不可获得焦点
      isFocusable = false
      isFocusableInTouchMode = false
    }
  }

  fun bindActivity(activity: AppCompatActivity) {
    owner = activity
    activity.lifecycle.addObserver(this)
  }

  override fun onResume(owner: LifecycleOwner) {

    //如果TextureView已经准备好，则直接使用
    if (binding.texture.isAvailable) {
      handleTexture()
    } else {
      setOnTextureAvailable { handleTexture() }
    }
  }

  override fun onPause(owner: LifecycleOwner) {

    //取消绘制
    drawJob?.cancel()
  }


  override fun onDestroy(owner: LifecycleOwner) {
    owner.lifecycle.removeObserver(this)
  }

  private fun handleTexture(){

    //开启协程，循环绘制
    drawJob = owner.lifecycleScope.launch(Dispatchers.Default){

      var curTime = System.currentTimeMillis()
      var lastTime = curTime

      while (isActive){
        synchronized(ScreenManager.stack){

          curTime = System.currentTimeMillis()
          ScreenManager.stack.peek().update(curTime-lastTime)
          lastTime = curTime

          val listIterator = ScreenManager.stack.listIterator(ScreenManager.stack.size)

          //找到第一个全屏窗口
          while (listIterator.hasPrevious()) {
            val tmp = listIterator.previous()
            if (!tmp.isPopup()) {
              break
            }
          }

          //绘制图像
          binding.texture.lockCanvas()?.let {

            try {
              while (listIterator.hasNext()) {
                listIterator.next().draw(it)
              }
            } finally {
              binding.texture.unlockCanvasAndPost(it)
            }
          }
        }

       delay(GAME_TIME_LOOP)
      }
    }
  }

  private fun setOnTextureAvailable(block: () -> Unit) {
    binding.texture.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
      override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}
      override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
      override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean = true
      override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        block.invoke()
      }
    }
  }

}