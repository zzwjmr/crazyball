package com.edu.seu.crazyball;

import static com.edu.seu.crazyball.Constant.*;

import org.jbox2d.dynamics.Body;

import android.graphics.Canvas;
import android.graphics.Paint;

public class MyRectColor extends MyBody{
	float halfwidth;
	float halfheight;
	public MyRectColor(Body body,float halfwidth,float halfheight,int color){
		this.body=body;
		this.halfwidth=halfwidth;
		this.halfheight=halfheight;
		this.color=color;
	}
	public void drawSelf(Canvas canvas,Paint paint){
		paint.setColor(color&0x8CFFFFFF);
		float x=body.getPosition().x*RATE;
		float y=body.getPosition().y*RATE;
		canvas.drawRect(x-halfwidth, y+halfheight, x+halfwidth, y-halfheight, paint);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1);
		paint.setColor(color);
		canvas.drawRect(x-halfwidth, y+halfheight, x+halfwidth, y-halfheight, paint);
		paint.reset();
		
	}
	public Body getRectBody(){
		return this.body;
	}
}
