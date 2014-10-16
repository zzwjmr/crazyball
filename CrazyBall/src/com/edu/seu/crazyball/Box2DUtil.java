package com.edu.seu.crazyball;

import static com.edu.seu.crazyball.Constant.*;           //��̬����  

import org.jbox2d.collision.CircleDef;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class Box2DUtil{                                 //���ɸ�����״�Ĺ�����  
	public static MyRectColor createBox(             
//������������(��ɫ)  
           float x,                                        //x����  
           float y,                                        //y����  
           float halfWidth,                                //���    
           float halfHeight,                               //���   
           boolean isStatic,                               //�Ƿ�Ϊ��ֹ��  
           World world,                                    //����  
           int color)                                      //��ɫ  
          	{                                         
          	PolygonDef shape = new PolygonDef();         
//�����������������   
          	if(isStatic){                               //�ж�isStatic�Ƿ�Ϊtrue  
          		shape.density = 0;                      //�ܶ�Ϊ0  
          	}else{  
          		shape.density = 2.0f;                   //�ܶ�Ϊ1.0f  
              	}  
          	shape.friction = 0.0f;                      //����Ħ��ϵ��  
          	shape.restitution = 1.0f;                       //����������ʧ��  
          	shape.setAsBox(halfWidth/RATE, halfHeight/RATE);   
          	BodyDef bodyDef = new BodyDef();                    //����������������  
          	bodyDef.position.set(x/RATE, y/RATE);               //����λ��  
          	Body bodyTemp= world.createBody(bodyDef);           //�������д�������  
          	bodyTemp.createShape(shape);     
          	bodyTemp.setMassFromShapes();                       //������������  
          	return new MyRectColor(bodyTemp,halfWidth,halfHeight,color);  
          	}  
     public static MyCircleColor createCircle(               //����Բ�Σ���ɫ��  
    		 float x,                                        //x����  
    		 float y,                                        //y����  
    		 float radius,                                   //�뾶  
    		 World world,                                    //����  
    		 int color){                                     //��ɫ  
          	CircleDef shape = new CircleDef();              //����Բ��������  
          	shape.density = 2;                              //�����ܶ�  
          	shape.friction = 0.0f;                          //����Ħ��ϵ��  
          	shape.restitution = 1.0f;                          //����������ʧ��  
          	shape.radius = radius/RATE;                     //���ð뾶  
          	BodyDef bodyDef = new BodyDef();                    //����������������  
          	bodyDef.position.set(x/RATE, y/RATE);               //����λ��  
          	Body bodyTemp = world.createBody(bodyDef);          //�������д�������  
          	bodyTemp.createShape(shape);                        //ָ��������״     
          	bodyTemp.setMassFromShapes();         
          	return new MyCircleColor(bodyTemp,radius,color); 
//����MyCircleColor����  
      }     
  }  