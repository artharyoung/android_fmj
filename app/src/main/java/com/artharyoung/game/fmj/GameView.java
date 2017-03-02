package com.artharyoung.game.fmj;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.artharyoung.game.fmj.graphics.TextRender;
import com.artharyoung.game.fmj.graphics.Util;
import com.artharyoung.game.fmj.lib.DatLib;
import com.artharyoung.game.fmj.scene.ScreenMainGame;
import com.artharyoung.game.fmj.script.ScriptProcess;
import com.artharyoung.game.fmj.views.BaseScreen;
import com.artharyoung.game.fmj.views.ScreenAnimation;
import com.artharyoung.game.fmj.views.ScreenMenu;
import com.artharyoung.game.fmj.views.ScreenSaveLoadGame;

import java.io.File;
import java.io.IOException;
import java.util.ListIterator;
import java.util.Stack;

public class GameView extends SurfaceView implements Callback, Runnable {
    private static final String TAG = "GameView";
    private SurfaceHolder mHolder;
    /**
     * 与SurfaceHolder绑定的Canvas
     */
    private Canvas mCanvas;
    /**
     * 用于绘制的线程
     */
    private Thread t;
    /**
     * 线程的控制开关
     */
    private boolean isRunning;
    private Context mContext;
    private static volatile GameView sInst = null;

    private Stack<BaseScreen> mScreenStack;

    public GameView(Context context) {
        this(context, null);

    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mHolder = getHolder();
        mHolder.addCallback(this);

        // setZOrderOnTop(true);// 设置画布 背景透明
        // mHolder.setFormat(PixelFormat.TRANSLUCENT);

        //设置可获得焦点
//        setFocusable(true);
//        setFocusableInTouchMode(true);
        //设置常亮
        this.setKeepScreenOn(true);
        sInst = this;

        //加载游戏
        try {
            DatLib.init(context);
            TextRender.init(context);
            Util.init();
            ScriptProcess.init();
            mScreenStack = new Stack<BaseScreen>();
            mScreenStack.push(new ScreenAnimation(248));
        } catch (IOException e) {
            Log.d(TAG, "========GameView: 初始化失败");
            e.printStackTrace();
        }
    }

    public static GameView getInstance() {
        return sInst;
    }

    /**
     * 提供存储游戏进度的路径
     * @param fileName
     * @return
     */
    public File saveLoadGame(String fileName){
        return mContext.getFileStreamPath(fileName);
    }

    public void changeScreen(int screenCode) {
        BaseScreen tmp = null;
        switch (screenCode) {
            case Global.SCREEN_DEV_LOGO:
                tmp = new ScreenAnimation(247);
                break;

            case Global.SCREEN_GAME_LOGO:
                tmp = new ScreenAnimation(248);
                break;

            case Global.SCREEN_MENU:
                tmp = new ScreenMenu();
                break;

            case Global.SCREEN_MAIN_GAME:
                tmp = new ScreenMainGame();
                break;

            case Global.SCREEN_GAME_FAIL:
                tmp = new ScreenAnimation(249);
                break;

            case Global.SCREEN_SAVE_GAME:
                tmp = new ScreenSaveLoadGame(ScreenSaveLoadGame.Operate.SAVE);
                break;

            case Global.SCREEN_LOAD_GAME:
                tmp = new ScreenSaveLoadGame(ScreenSaveLoadGame.Operate.LOAD);
                break;
        }
        if (tmp != null) {
            mScreenStack.clear();
            mScreenStack.push(tmp);
        }
        System.gc();
    }

    public void pushScreen(BaseScreen screen) {
        mScreenStack.push(screen);
    }

    public void popScreen() {
        mScreenStack.pop();
    }

    public BaseScreen getCurScreen() {
        return mScreenStack.peek();
    }

    public void keyDown(int key) {
        synchronized (mScreenStack) {
            mScreenStack.peek().onKeyDown(key);
        }
    }

    public void keyUp(int key) {
        synchronized (mScreenStack) {
            mScreenStack.peek().onKeyUp(key);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //设置分辨率为游戏本身大小。便于适配
        holder.setFixedSize(Global.SCREEN_WIDTH,Global.SCREEN_HEIGHT);
        // 开启线程
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // 通知关闭线程
        isRunning = false;
    }

    @Override
    public void run() {

        long curTime = System.currentTimeMillis();
        long lastTime = curTime;
        while (isRunning) {
            if (mScreenStack != null) {
                synchronized (mScreenStack) {
                    curTime = System.currentTimeMillis();
                    mScreenStack.peek().update(curTime - lastTime);
                    lastTime = curTime;

                    ListIterator<BaseScreen> iter = mScreenStack.listIterator(mScreenStack.size());
                    // 找到第一个全屏窗口
                    while (iter.hasPrevious()) {
                        BaseScreen tmp = iter.previous();
                        if (!tmp.isPopup()) {
                            break;
                        }
                    }

                    try {
                        // 获得canvas
                        mCanvas = mHolder.lockCanvas();
                        if (mCanvas != null) {
                            // drawSomething..
                            while (iter.hasNext()) {
                                iter.next().draw(mCanvas);
                            }
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "=========draw: boom");
                    } finally {
                        if (mCanvas != null)
                            mHolder.unlockCanvasAndPost(mCanvas);
                    }
                }
            }
            try {
                Thread.sleep(Global.TIME_GAMELOOP);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }

}
