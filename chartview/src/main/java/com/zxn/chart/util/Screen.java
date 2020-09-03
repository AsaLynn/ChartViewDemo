package com.zxn.chart.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;

public class Screen {

	static final Screen SCREEN = new Screen();

	public int widthPixels;
	public int heightPixels;
	public static int barHeight;
	public float density;
	public float scaledDensity;
	public int densityDpi;
	public float xdpi;
	public float ydpi;

	private Screen() {

	}

	public static void initScreen(Context context) {
		DisplayMetrics display = context.getResources().getDisplayMetrics();
		SCREEN.widthPixels = display.widthPixels;
		SCREEN.heightPixels = display.heightPixels;
		SCREEN.density = display.density;
		SCREEN.scaledDensity = display.scaledDensity;
		SCREEN.densityDpi = display.densityDpi;
//		LogUtil.w(SCREEN.widthPixels+"/"+SCREEN.heightPixels+"/"+SCREEN.densityDpi+"");
		SCREEN.xdpi = display.xdpi;
		SCREEN.ydpi = display.ydpi;
	}


	public static int getStatusHeight(Activity activity){
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		barHeight = rect.top;
		if (0 == barHeight){
			try {
				Class<?> localClass = Class.forName("com.android.internal.R$dimen");
				Object localObject = localClass.newInstance();
				int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
				barHeight = activity.getResources().getDimensionPixelSize(i5);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return barHeight;
	}

	public static void setBarHeight(int barHeight) {
		getInstance();
		Screen.barHeight = barHeight;
	}

	public static Screen getInstance() {
		return SCREEN;
	}
}
