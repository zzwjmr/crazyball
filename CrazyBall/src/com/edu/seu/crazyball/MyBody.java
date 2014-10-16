package com.edu.seu.crazyball;

import org.jbox2d.dynamics.Body;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class MyBody {
	Body body;
	int color;
	public abstract void drawSelf(Canvas canvas,Paint paint);
}
