package com.artharyoung.game.fmj;


import android.graphics.Color;

public class Global {
	public static int COLOR_WHITE = Color.argb(255, 199, 237, 204);
	public static int COLOR_BLACK = Color.argb(255, 0, 0, 0);
	public static int COLOR_TRANSP = Color.argb(0, 0, 0, 0);

	public static final int SCREEN_WIDTH = 159;//游戏实际像素宽 159  160
	public static final int SCREEN_HEIGHT = 96;//游戏实际像素高 96   106
	
	
	public static final int MAP_LEFT_OFFSET = 8;
	
	public static final int KEY_UP = 1;
	public static final int KEY_DOWN = 2;
	public static final int KEY_LEFT = 3;
	public static final int KEY_RIGHT = 4;
	public static final int KEY_PAGEUP = 5;
	public static final int KEY_PAGEDOWN = 6;
	public static final int KEY_ENTER = 7;
	public static final int KEY_CANCEL = 8;
	
	public static final long TIME_GAMELOOP = 45;
	
	public static final int SCREEN_DEV_LOGO = 1;
	public static final int SCREEN_GAME_LOGO = 2;
	public static final int SCREEN_MENU = 3;
	public static final int SCREEN_MAIN_GAME = 4;
	public static final int SCREEN_GAME_FAIL = 5;
	public static final int SCREEN_SAVE_GAME = 6;
	public static final int SCREEN_LOAD_GAME = 7;
}
