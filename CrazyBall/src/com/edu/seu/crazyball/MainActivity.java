package com.edu.seu.crazyball;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import static com.edu.seu.crazyball.Constant.*;

public class MainActivity extends Activity  {



	public void onCreate(Bundle savedInstanceState) {
		// 继承Activity类需要重写的方法
		super.onCreate(savedInstanceState); // 调用父类
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置为全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// setRequestedOrientation(ActivityInfo. SCREEN_ORIENTATION_PORTRAIT);
		// 设置为横屏模式
//		DisplayMetrics dm = new DisplayMetrics(); // 获取屏幕尺寸
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		// 得到屏幕的宽度和高度
//		if (dm.widthPixels < dm.heightPixels) { // 判断宽度与高度的大小
//			SCREEN_WIDTH = dm.widthPixels; // 为SCREEN_WIDTH赋值
//			SCREEN_HEIGHT = dm.heightPixels; // 为SCREEN_HEIGHT赋值
//		} else {
//			SCREEN_WIDTH = dm.heightPixels; // 为SCREEN_WIDTH赋值
//			SCREEN_HEIGHT = dm.widthPixels; // 为SCREEN_HEIGHT赋值
//		}
		
		GameView gv = new GameView(this); // 创建GameView对象
		setContentView(gv); // 跳转到GameView界面
		
	}

	
}
