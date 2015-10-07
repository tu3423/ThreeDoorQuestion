package com.example.ce_ztzheng.threedoorquestion;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    /**
     * version 1.4
     */

    Button bu;
    EditText et;
    TextView tv;
    math_until mu = null;
    Handler h;
    private ProgressDialog mpDialog;
    private int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        bu = (Button) findViewById(R.id.button);
        et = (EditText) findViewById(R.id.editText);
        tv = (TextView) findViewById(R.id.textView);
        mu = new math_until();
        String setting = "{\"getonline\":[" + "{\"t\":\"empty\",\"T\":\"empty\"}],"+"\"getonline2\":[" + "{\"t\":\"empt21y\",\"T\":\"empt23y\"}]}";
        JSONObject json = null;
        try {
            json = new JSONObject(setting);
            JSONArray jsonArray = json.getJSONArray("getonline2");
         for(int i=0;i<jsonArray.length();i++){
             JSONObject temperatureobject = (JSONObject)jsonArray.get(i);
           String  time = temperatureobject.getString("t");
            String  temperature = temperatureobject.getString("T");
             System.out.println(temperatureobject);
         }
        } catch (JSONException e) {
            e.printStackTrace();
        }//changedefdasfdafs

        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!et.getText().toString().equals("") && Long.parseLong(et.getText().toString()) > 0) {
                    mu.flag = false;
                    mu.get_percent(Long.parseLong(et.getText().toString()), h);

                    mCount = 0;
                    mpDialog = new ProgressDialog(MainActivity.this);
                    mpDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mpDialog.setTitle("提示");
                    mpDialog.setIcon(R.mipmap.ic_launcher);
                    mpDialog.setMessage("请等待");
                    mpDialog.setMax(100);
                    mpDialog.setProgress(0);
                    mpDialog.setIndeterminate(false);
                    mpDialog.setCancelable(true);
                    mpDialog.setButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mu.flag = true;
                            dialog.cancel();

                        }

                    });
                    mpDialog.setCancelable(false);
                    mpDialog.show();
                }

            }
        });
        h = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:

                        mpDialog.setProgress(msg.getData().getInt("process"));
                        if (msg.getData().getInt("process") % 3 == 0) {
                            mpDialog.setMessage("请等待.");
                        } else if (msg.getData().getInt("process") % 3 == 1) {
                            mpDialog.setMessage("请等待..");
                        } else if (msg.getData().getInt("process") % 3 == 2) {
                            mpDialog.setMessage("请等待...");
                        }
                        break;
                    case 1:
                        tv.setText(msg.getData().getString("res"));
                        mpDialog.cancel();
                        break;
                }
            }
        };
    }


}
