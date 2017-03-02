package com.artharyoung.game.fmj;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity implements View.OnTouchListener ,View.OnLongClickListener{
    private static final String TAG = "MainActivity";
    public static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private Handler mHandler = null;

    private static volatile int mkeyCode = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        ViewGroup gameDisplay = (ViewGroup)findViewById(R.id.game_show);

        //获取屏幕像素相关信息
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int scale = dm.widthPixels/Global.SCREEN_WIDTH;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Global.SCREEN_WIDTH*scale, Global.SCREEN_HEIGHT * scale);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        gameDisplay.setLayoutParams(params);

        initGameControl();

        mHandler = new MyHandler(this);

        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(1);
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    private static class MyHandler extends Handler {
            private WeakReference<MainActivity> activityWeakReference;

            public MyHandler(MainActivity activity) {
                activityWeakReference = new WeakReference<MainActivity>(activity);
            }

            @Override
            public void handleMessage(Message msg) {
                MainActivity activity = activityWeakReference.get();
                if (activity != null) {

                    activity.walk();
                }
            }
        }

    private void walk() {

        if (mkeyCode != -1) {
            GameView.getInstance().keyUp(mkeyCode);
            GameView.getInstance().keyDown(mkeyCode);
        }
    }


    private void initGameControl() {
        ImageButton btnUp = (ImageButton) findViewById(R.id.game_up);
        btnUp.setOnTouchListener(this);
        btnUp.setOnLongClickListener(this);
        ImageButton btnDown = (ImageButton) findViewById(R.id.game_down);
        btnDown.setOnTouchListener(this);
        btnDown.setOnLongClickListener(this);
        ImageButton btnLeft = (ImageButton) findViewById(R.id.game_left);
        btnLeft.setOnTouchListener(this);
        btnLeft.setOnLongClickListener(this);
        ImageButton btnRight = (ImageButton) findViewById(R.id.game_right);
        btnRight.setOnTouchListener(this);
        btnRight.setOnLongClickListener(this);
        ImageButton btnEnter = (ImageButton) findViewById(R.id.game_enter);
        btnEnter.setOnTouchListener(this);
        ImageButton btnCancel = (ImageButton) findViewById(R.id.game_cancel);
        btnCancel.setOnTouchListener(this);
        ImageButton btnPageUp = (ImageButton) findViewById(R.id.game_page_up);
        btnPageUp.setOnTouchListener(this);
        ImageButton btnPageDown = (ImageButton) findViewById(R.id.game_page_down);
        btnPageDown.setOnTouchListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.game_up:
                onPressed(Global.KEY_UP);
                break;
            case R.id.game_down:
                onPressed(Global.KEY_DOWN);
                break;
            case R.id.game_left:
                onPressed(Global.KEY_LEFT);
                break;
            case R.id.game_right:
                onPressed(Global.KEY_RIGHT);
                break;
            default:
                break;
        }

        return false;
    }

    /**
     *
     * @param keyCode
     */
    public void onPressed(int keyCode){

        mkeyCode = keyCode;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.game_up:
                gameKeyEvent(Global.KEY_UP, event);
                break;
            case R.id.game_down:
                gameKeyEvent(Global.KEY_DOWN, event);
                break;
            case R.id.game_left:
                gameKeyEvent(Global.KEY_LEFT, event);
                break;
            case R.id.game_right:
                gameKeyEvent(Global.KEY_RIGHT, event);
                break;
            case R.id.game_enter:
                gameKeyEvent(Global.KEY_ENTER, event);
                break;
            case R.id.game_cancel:
                gameKeyEvent(Global.KEY_CANCEL, event);
                break;
            case R.id.game_page_up:
                gameKeyEvent(Global.KEY_PAGEUP, event);
                break;
            case R.id.game_page_down:
                gameKeyEvent(Global.KEY_PAGEDOWN, event);
                break;
            default:
                break;
        }
        return false;
    }

    private void gameKeyEvent(int keyCode, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                GameView.getInstance().keyUp(keyCode);
                mkeyCode = -1;
                break;
            case MotionEvent.ACTION_DOWN:
                GameView.getInstance().keyDown(keyCode);
                break;
            default:
                break;
        }
    }
}
