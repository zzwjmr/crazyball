package com.edu.seu.crazyball2;

import static com.edu.seu.crazyball2.Constant.*;


import com.badlogic.gdx.backends.android.AndroidApplication;


import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;



public class GdxApplication extends AndroidApplication {
    /** Called when the activity is first created. */
	
	public Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
		};
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		DisplayMetrics dm = new DisplayMetrics(); // ��ȡ��Ļ�ߴ�
	    getWindowManager().getDefaultDisplay().getMetrics(dm);
		// �õ���Ļ�Ŀ�Ⱥ͸߶�
		if (dm.widthPixels < dm.heightPixels) { // �жϿ����߶ȵĴ�С
			SCREEN_WIDTH = dm.widthPixels; // ΪSCREEN_WIDTH��ֵ
			SCREEN_HEIGHT = dm.heightPixels; // ΪSCREEN_HEIGHT��ֵ
		} else {
			SCREEN_WIDTH = dm.heightPixels; // ΪSCREEN_WIDTH��ֵ
			SCREEN_HEIGHT = dm.widthPixels; // ΪSCREEN_HEIGHT��ֵ
		}
        initialize(new GdxAppUI(), false);
    }
}