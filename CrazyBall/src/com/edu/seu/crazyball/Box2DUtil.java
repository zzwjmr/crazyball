package com.edu.seu.crazyball;

import static com.edu.seu.crazyball.Constant.*;           //静态导入  

import org.jbox2d.collision.CircleDef;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class Box2DUtil{                                 //生成刚体形状的工具类  
	public static MyRectColor createBox(             
//创建矩形物体(颜色)  
           float x,                                        //x坐标  
           float y,                                        //y坐标  
           float halfWidth,                                //半宽    
           float halfHeight,                               //半高   
           boolean isStatic,                               //是否为静止的  
           World world,                                    //世界  
           int color)                                      //颜色  
          	{                                         
          	PolygonDef shape = new PolygonDef();         
//创建多边形描述对象   
          	if(isStatic){                               //判断isStatic是否为true  
          		shape.density = 0;                      //密度为0  
          	}else{  
          		shape.density = 2.0f;                   //密度为1.0f  
              	}  
          	shape.friction = 0.0f;                      //设置摩擦系数  
          	shape.restitution = 1.0f;                       //设置能量损失率  
          	shape.setAsBox(halfWidth/RATE, halfHeight/RATE);   
          	BodyDef bodyDef = new BodyDef();                    //创建刚体描述对象  
          	bodyDef.position.set(x/RATE, y/RATE);               //设置位置  
          	Body bodyTemp= world.createBody(bodyDef);           //在世界中创建刚体  
          	bodyTemp.createShape(shape);     
          	bodyTemp.setMassFromShapes();                       //设置物体质量  
          	return new MyRectColor(bodyTemp,halfWidth,halfHeight,color);  
          	}  
     public static MyCircleColor createCircle(               //创建圆形（颜色）  
    		 float x,                                        //x坐标  
    		 float y,                                        //y坐标  
    		 float radius,                                   //半径  
    		 World world,                                    //世界  
    		 int color){                                     //颜色  
          	CircleDef shape = new CircleDef();              //创建圆描述对象  
          	shape.density = 2;                              //设置密度  
          	shape.friction = 0.0f;                          //设置摩擦系数  
          	shape.restitution = 1.0f;                          //设置能量损失率  
          	shape.radius = radius/RATE;                     //设置半径  
          	BodyDef bodyDef = new BodyDef();                    //创建刚体描述对象  
          	bodyDef.position.set(x/RATE, y/RATE);               //设置位置  
          	Body bodyTemp = world.createBody(bodyDef);          //在世界中创建刚体  
          	bodyTemp.createShape(shape);                        //指定刚体形状     
          	bodyTemp.setMassFromShapes();         
          	return new MyCircleColor(bodyTemp,radius,color); 
//返回MyCircleColor对象  
      }     
  }  