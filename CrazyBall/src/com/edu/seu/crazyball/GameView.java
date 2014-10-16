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

public class GameView extends SurfaceView implements SurfaceHolder.Callback,ContactListener{ // ������
	MainActivity activity; // ��Ա����
	Paint paint; // ����
	DrawThread dt; // �����߳�
	World world; // ��Ա����world
	private Body ground;
	private Body board;
	AABB worldAABB; // ���� һ��������ײ������
	ArrayList<MyBody> bl = new ArrayList<MyBody>(); // �洢�����б�ļ���
	final int bound = 5; // 1/2�߽��
	MouseJoint mj;
	
	public GameView(MainActivity activity) { // ������
		super(activity); // ���ø���
		
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
		
		worldAABB = new AABB(); // ����AABB����
		worldAABB.lowerBound.set(-100.0f, -100.0f); // ���ð�Χ�����±߽�
		worldAABB.upperBound.set(100.0f, 100.0f); // ���ð�Χ�����ϱ߽�
		Vec2 gravity = new Vec2(0.0f, 0.0f); // �����������ٶ�
		boolean doSleep = true; // ����doSleepΪtrue
		world = new World(worldAABB, gravity, doSleep); // ��������
		final int kd = 40; // ��Ȼ�߶�
		
		MyRectColor mrc = Box2DUtil.createBox // ��߽�
				(bound, SCREEN_WIDTH / 2, bound, SCREEN_WIDTH / 2, true, world,
						Color.BLACK);
		bl.add(mrc); // ��ӽ�������
		mrc = Box2DUtil.createBox // �ұ߽�
				(SCREEN_WIDTH - bound, SCREEN_WIDTH / 2, bound,
						SCREEN_WIDTH / 2, true, world, Color.BLACK);
		bl.add(mrc);
		mrc = Box2DUtil.createBox // �ϱ߽�
				(SCREEN_WIDTH / 2, bound, SCREEN_WIDTH / 2, bound, true, world,
						Color.BLACK);
		bl.add(mrc);
		mrc = Box2DUtil.createBox // �±߽�
				(SCREEN_WIDTH / 2, SCREEN_WIDTH - bound, SCREEN_WIDTH / 2,
						bound, true, world, Color.BLACK);
		bl.add(mrc);

		MyRectColor mrc_board = Box2DUtil.createBox // ����
				(SCREEN_WIDTH / 2, SCREEN_WIDTH - bound * 2
						- (BOARD_HEIGHT / 2), SCREEN_WIDTH * BOARD_WIDTH_RATE
						/ 2, BOARD_HEIGHT / 2, false, world, Color.RED);
		bl.add(mrc_board);

		ground = mrc.getRectBody();
		board = mrc_board.getRectBody();

		MyCircleColor ball = Box2DUtil.createCircle // ������
				(SCREEN_WIDTH / 2 - 24, kd, kd / 2, world, Color.BLACK);
		bl.add(ball); // ��ӽ�������
		ball.body.setLinearVelocity(new Vec2(80, 100)); // ���ó��ٶ�
		
		PrismaticJointDef jointDef=new PrismaticJointDef();
		Vec2 axis=new Vec2(1.0f,0.0f);
		//jointDef.collideConnected=true;
		jointDef.initialize(board, ground, board.getWorldCenter(), axis);
		world.createJoint(jointDef);
		
		
		
		this.activity = activity; // ��ʼ����Ա����
		this.getHolder().addCallback(this); // �����������ڻص��ӿڵ�ʵ����
		board.wakeUp();
		
		
		paint = new Paint(); // ��������
		paint.setAntiAlias(true); // �򿪿����
		dt = new DrawThread(this); // �����̶߳���
		dt.start(); // �����߳�
		
	}

	public void onDraw(Canvas canvas) { // ���Ʒ���
		if (canvas == null) { // �ж�canvas�Ƿ�Ϊ��
			return; // canvasΪ���򷵻�
		}
		canvas.drawARGB(255, 255, 255, 255); // ���ñ�����ɫ
		for (MyBody mb : bl) { // ���Ƴ����е�����
			mb.drawSelf(canvas, paint); // ��������ľ�������
		}
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		repaint();
	}

	// ����ʱ������
	public void surfaceDestroyed(SurfaceHolder arg0) {
	} // ����ʱ������

	@SuppressLint("WrongCall")
	public void repaint() {
		SurfaceHolder holder = this.getHolder();
		// �õ��ص��ӿڵĶ���
		Canvas canvas = holder.lockCanvas(); // ��ȡ����
		try {
			synchronized (holder) { // ͬ������
				onDraw(canvas); // ����
			}
		} catch (Exception e) { // �����쳣
			e.printStackTrace(); // ��ӡ��ջ��Ϣ
		} finally {
			if (canvas != null) {
				// �ж�canvas�Ƿ�Ϊ��
				holder.unlockCanvasAndPost(canvas); // ����
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