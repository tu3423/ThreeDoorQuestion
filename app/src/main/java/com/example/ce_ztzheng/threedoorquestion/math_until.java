package com.example.ce_ztzheng.threedoorquestion;

import android.os.Handler;
import android.os.Message;

import java.util.Random;

/**
 * Created by ce-ztzheng on 2015/10/6.
 */
public class math_until {
    long account;
    Handler myhandler;
    public boolean flag=false;
    public void get_percent(long count,Handler h) {
        this.account=count;
        myhandler=h;
    new Thread(new Runnable() {
        @Override
        public void run() {
            Random r = new Random();
            int select;
            int answer;
            int select2;
            int answer2;
            int process=0;
           int process2;
            double rightnum = 0.0;
            double rightnum2 = 0.0;
            for (int i = 0; i < account; i++) {
                if(flag) {
                    break;
                }
                //换
                select = r.nextInt(3) + 1;
                answer = r.nextInt(3) + 1;
                if (select != answer) {
                    rightnum++;
                }
                //不换
                select2 = r.nextInt(3) + 1;
                answer2 = r.nextInt(3) + 1;
                if (select2 == answer2) {
                    rightnum2++;
                }
                Message mes=new Message();
                mes.what=0;
                android.os.Bundle b=new android.os.Bundle();
                process2= (int)( (i/(double)account)*100);
                if(process!=process2) {
                    process=process2;
                    b.putInt("process", process);
                    mes.setData(b);
                    myhandler.sendMessage(mes);
                }
            }//test
            if(!flag) {
                String restring = "换的概率： " +Math.round(( rightnum / account * 100)*1000000)/1000000.0 + "% \n" +
                        "不换的概率： " + Math.round(( rightnum2 / account * 100)*1000000)/1000000.0 + "% \n";
                Message mes = new Message();
                mes.what = 1;
                android.os.Bundle b = new android.os.Bundle();

                b.putString("res", restring);
                mes.setData(b);
                myhandler.sendMessage(mes);
            }
        }
    }).start();


    }
}
