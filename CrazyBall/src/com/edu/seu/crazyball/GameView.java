package com.edu.seu.crazyball;

import static com.edu.seu.crazyball.Constant.BOARD_HEIGHT;
import static com.edu.seu.crazyball.Constant.*;
import static com.edu.seu.crazyball.Constant.BOARD_WIDTH_RATE;
import static com.edu.seu.crazyball.Constant.SCREEN_WIDTH;

import java.util.ArrayList;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.ContactListener;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.ContactPoint;
import org.jbox2d.dynamics.contacts.ContactResult;
import org.jbox2d.dynamics.joints.JointDef;
import org.jbox2d.dynamics.joints.MouseJoint;
import org.jbox2d.dynamics.joints.MouseJointDef;
import org.jbox2d.dynamics.joints.PrismaticJointDef;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,ContactListener{ // 创建类
	MainActivity activity; // 成员变量
	Paint paint; // 画笔
	DrawThread dt; // 绘制线程
	World world; // 成员变量world
	private Body ground;
	private Body board;
	AABB worldAABB; // 创建 一个管理碰撞的世界
	ArrayList<MyBody> bl = new ArrayList<MyBody>(); // 存储物体列表的集合
	final int bound = 5; // 1/2边界宽
	MouseJoint mj;
	
	public GameView(MainActivity activity) { // 构造器
		super(activity); // 调用父类
		
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
		
		worldAABB = new AABB(); // 创建AABB对象
		worldAABB.lowerBound.set(-100.0f, -100.0f); // 设置包围盒子下边界
		worldAABB.upperBound.set(100.0f, 100.0f); // 设置包围盒子上边界
		Vec2 gravity = new Vec2(0.0f, 0.0f); // 设置重力加速度
		boolean doSleep = true; // 设置doSleep为true
		world = new World(worldAABB, gravity, doSleep); // 创建世界
		final int kd = 40; // 宽度或高度
		
		MyRectColor mrc = Box2DUtil.createBox // 左边界
				(bound, SCREEN_WIDTH / 2, bound, SCREEN_WIDTH / 2, true, world,
						Color.BLACK);
		bl.add(mrc); // 添加进集合中
		mrc = Box2DUtil.createBox // 右边界
				(SCREEN_WIDTH - bound, SCREEN_WIDTH / 2, bound,
						SCREEN_WIDTH / 2, true, world, Color.BLACK);
		bl.add(mrc);
		mrc = Box2DUtil.createBox // 上边界
				(SCREEN_WIDTH / 2, bound, SCREEN_WIDTH / 2, bound, true, world,
						Color.BLACK);
		bl.add(mrc);
		mrc = Box2DUtil.createBox // 下边界
				(SCREEN_WIDTH / 2, SCREEN_WIDTH - bound, SCREEN_WIDTH / 2,
						bound, true, world, Color.BLACK);
		bl.add(mrc);

		MyRectColor mrc_board = Box2DUtil.createBox // 滑板
				(SCREEN_WIDTH / 2, SCREEN_WIDTH - bound * 2
						- (BOARD_HEIGHT / 2), SCREEN_WIDTH * BOARD_WIDTH_RATE
						/ 2, BOARD_HEIGHT / 2, false, world, Color.RED);
		bl.add(mrc_board);

		ground = mrc.getRectBody();
		board = mrc_board.getRectBody();

		MyCircleColor ball = Box2DUtil.createCircle // 创建球
				(SCREEN_WIDTH / 2 - 24, kd, kd / 2, world, Color.BLACK);
		bl.add(ball); // 添加进集合中
		ball.body.setLinearVelocity(new Vec2(80, 100)); // 设置初速度
		
		PrismaticJointDef jointDef=new PrismaticJointDef();
		Vec2 axis=new Vec2(1.0f,0.0f);
		//jointDef.collideConnected=true;
		jointDef.initialize(board, ground, board.getWorldCenter(), axis);
		world.createJoint(jointDef);
		
		
		
		this.activity = activity; // 初始化成员变量
		this.getHolder().addCallback(this); // 设置生命周期回调接口的实现者
		board.wakeUp();
		
		
		paint = new Paint(); // 创建画笔
		paint.setAntiAlias(true); // 打开抗锯齿
		dt = new DrawThread(this); // 创建线程对象
		dt.start(); // 开启线程
		
	}

	public void onDraw(Canvas canvas) { // 绘制方法
		if (canvas == null) { // 判断canvas是否为空
			return; // canvas为空则返回
		}
		canvas.drawARGB(255, 255, 255, 255); // 设置背景颜色
		for (MyBody mb : bl) { // 绘制场景中的物体
			mb.drawSelf(canvas, paint); // 绘制球与木块金字塔
		}
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		repaint();
	}

	// 创建时被调用
	public void surfaceDestroyed(SurfaceHolder arg0) {
	} // 销毁时被调用

	@SuppressLint("WrongCall")
	public void repaint() {
		SurfaceHolder holder = this.getHolder();
		// 得到回调接口的对象
		Canvas canvas = holder.lockCanvas(); // 获取画布
		try {
			synchronized (holder) { // 同步处理
				onDraw(canvas); // 绘制
			}
		} catch (Exception e) { // 捕获异常
			e.printStackTrace(); // 打印堆栈信息
		} finally {
			if (canvas != null) {
				// 判断canvas是否为空
				holder.unlockCanvasAndPost(canvas); // 解锁
			}
		}
	}

	
	public boolean onTouchEvent(MotionEvent event){
		
		MouseJointDef mousejoint = new MouseJointDef();
		mousejoint.body1 = world.getGroundBody();
		mousejoint.body2 = board;
		mousejoint.target.x = board.getPosition().x;
		mousejoint.target.y = board.getPosition().y;
		System.out.println("lalallaalala:"+board.getPosition().x+"  "+board.getPosition().y);
		mousejoint.collideConnected = true;
		mousejoint.maxForce = 10000.0f;
		mj = (MouseJoint) world.createJoint(mousejoint);
		board.wakeUp();
		mj.setTarget(new Vec2(event.getX()/RATE, SCREEN_WIDTH - bound * 2
				- (BOARD_HEIGHT / 2)));
	
		
		return true;
	}
	public boolean  touchDragged(int x,int y,int p) {
		
		return true;	
	}

	@Override
	public void add(ContactPoint arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persist(ContactPoint arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(ContactPoint arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void result(ContactResult arg0) {
		// TODO Auto-generated method stub
		
	}}