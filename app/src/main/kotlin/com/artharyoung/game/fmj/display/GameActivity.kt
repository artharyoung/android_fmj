package com.artharyoung.game.fmj.display

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.artharyoung.game.fmj.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

  private lateinit var binding: ActivityGameBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityGameBinding.inflate(layoutInflater)
    setContentView(binding.root)

    initView()
    initData()
  }

  override fun onResume() {
    super.onResume()
    setStatusBar()
  }

  private fun initView(){
    binding.displayView.bindActivity(this)

  }

  private fun initData(){

  }



  //设置状态栏
  private fun setStatusBar() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or
        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
  }



}