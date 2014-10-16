package com.edu.seu.crazyball;

import static com.edu.seu.crazyball.Constant.*;

public class DrawThread extends Thread{                     //�����߳�  
       GameView gv;                                        //GameView����  
       public DrawThread(GameView gv){                         //������  
           this.gv=gv;                                 //��ʼ����Ա����  
       }     
       @Override  
       public void run(){                              //��д��run()����  
          while(DRAW_THREAD_FLAG){                       
//�жϱ�־λ�Ƿ�Ϊtrue  
              gv.world.step(TIME_STEP, ITERA);       //��ʼģ��  
              gv.repaint();                               //ˢ֡  
              try {                     
                  Thread.sleep(20);                     
//�߳���Ϣ20ms  
              }catch (InterruptedException e){           
//�����쳣  
                  e.printStackTrace();                 
//��ӡ��ջ��Ϣ  
  } } } }  