package com.edu.seu.crazyball;

import static com.edu.seu.crazyball.Constant.*;

public class DrawThread extends Thread{                     //绘制线程  
       GameView gv;                                        //GameView对象  
       public DrawThread(GameView gv){                         //构造器  
           this.gv=gv;                                 //初始化成员变量  
       }     
       @Override  
       public void run(){                              //重写的run()方法  
          while(DRAW_THREAD_FLAG){                       
//判断标志位是否为true  
              gv.world.step(TIME_STEP, ITERA);       //开始模拟  
              gv.repaint();                               //刷帧  
              try {                     
                  Thread.sleep(20);                     
//线程休息20ms  
              }catch (InterruptedException e){           
//捕获异常  
                  e.printStackTrace();                 
//打印堆栈信息  
  } } } }  