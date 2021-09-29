package com.artharyoung.game.fmj

import android.text.TextUtils
import android.util.Log

object LogUtil {

  private const val CUSTOM_TAG_PREFIX = ""

  private const val isDebug: Boolean = GAME_DEBUG
  private const val allowD = isDebug
  private const val allowE = isDebug
  private const val allowI = isDebug
  private const val allowV = isDebug
  private const val allowW = isDebug
  private const val allowWtf = isDebug

  private fun generateTag(caller: StackTraceElement): String {
    val className = caller.className
    val callerClazzName = className.substring(className.lastIndexOf(".") + 1)
    val tag = String.format("%s.%s(L:%d)", callerClazzName, caller.methodName, caller.lineNumber)
    return if (TextUtils.isEmpty(CUSTOM_TAG_PREFIX)) tag else "$CUSTOM_TAG_PREFIX:$tag"
  }

  private fun getCallerStackTraceElement(): StackTraceElement {
    return Thread.currentThread().stackTrace[4]
  }

  fun v(content: String?) {
    if (!allowV) return
    val caller = getCallerStackTraceElement()
    val tag = generateTag(caller)
    Log.v(tag, "$content")
  }

  fun v(tag: String?, content: String?) {
    if (!allowI) return
    Log.v(tag, "$content")
  }

  fun d(content: String?) {
    if (!allowD) return
    val caller = getCallerStackTraceElement()
    val tag = generateTag(caller)
    Log.d(tag, "$content")
  }

  fun d(tag: String, content: String?) {
    if (!allowD) return
    Log.d(tag, "$content")
  }

  fun i(content: String?) {
    if (!allowI) return
    val caller = getCallerStackTraceElement()
    val tag = generateTag(caller)
    Log.i(tag, "$content")
  }

  fun i(tag: String?, content: String?) {
    if (!allowI) return
    Log.i(tag, "$content")
  }

  fun w(content: String?) {
    if (!allowW) return
    val caller = getCallerStackTraceElement()
    val tag = generateTag(caller)
    Log.w(tag, "$content")
  }

  fun w(tag: String?, content: String?) {
    if (!allowI) return
    Log.w(tag, "$content")
  }

  fun e(log: String?) {
    if (!allowE) return
    val caller = getCallerStackTraceElement()
    val tag = generateTag(caller)
    val segmentSize = 3 * 1024
    val length = log.orEmpty().length.toLong()
    if (length <= segmentSize) { // 长度小于等于限制直接打印
      Log.e(tag, "$log")
    } else {
      var content = "$log"
      while (content.length > segmentSize) { // 循环分段打印日志
        val logContent = content.substring(0, segmentSize)
        content = content.replace(logContent, "")
        Log.e(tag, logContent)
      }
      Log.e(tag, content) // 打印剩余日志
    }
  }

  fun e(tag: String?, log: String?) {
    if (!allowE) return
    val segmentSize = 3 * 1024
    val length = log.orEmpty().length.toLong()
    if (length <= segmentSize) { // 长度小于等于限制直接打印
      Log.e(tag, "$log")
    } else {
      var content = "$log"
      while (content.length > segmentSize) { // 循环分段打印日志
        val logContent = content.substring(0, segmentSize)
        content = content.replace(logContent, "")
        Log.e(tag, logContent)
      }
      Log.e(tag, content) // 打印剩余日志
    }
  }

  fun wtf(content: String?) {
    if (!allowWtf) return
    val caller = getCallerStackTraceElement()
    val tag = generateTag(caller)
    Log.wtf(tag, content)
  }
}