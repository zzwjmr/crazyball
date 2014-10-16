package com.edu.seu.crazyball;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import static com.edu.seu.crazyball.Constant.*;

public class MainActivity extends Activity  {



	public void onCreate(Bundle savedInstanceState) {
		// �̳�Activity����Ҫ��д�ķ���
		super.onCreate(savedInstanceState); // ���ø���
		requestWindowFeature(Window.FEATURE_NO_TITLE); // ����Ϊȫ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// setRequestedOrientation(ActivityInfo. SCREEN_ORIENTATION_PORTRAIT);
		// ����Ϊ����ģʽ
//		DisplayMetrics dm = new DisplayMetrics(); // ��ȡ��Ļ�ߴ�
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		// �õ���Ļ�Ŀ�Ⱥ͸߶�
//		if (dm.widthPixels < dm.heightPixels) { // �жϿ����߶ȵĴ�С
//			SCREEN_WIDTH = dm.widthPixels; // ΪSCREEN_WIDTH��ֵ
//			SCREEN_HEIGHT = dm.heightPixels; // ΪSCREEN_HEIGHT��ֵ
//		} else {
//			SCREEN_WIDTH = dm.heightPixels; // ΪSCREEN_WIDTH��ֵ
//			SCREEN_HEIGHT = dm.widthPixels; // ΪSCREEN_HEIGHT��ֵ
//		}
		
		GameView gv = new GameView(this); // ����GameView����
		setContentView(gv); // ��ת��GameView����
		
	}

	
}
