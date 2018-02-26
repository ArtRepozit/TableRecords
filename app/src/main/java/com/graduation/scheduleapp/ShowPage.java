package com.graduation.scheduleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class ShowPage extends Activity implements View.OnClickListener{

    TextView showGet;
    Button buGet;

    Handler handler;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_table);
        showGet = (TextView) findViewById(R.id.show_get);
        buGet = (Button) findViewById(R.id.bu_get);
            buGet.setOnClickListener(this);

        handler = new Handler();
    }
    public void onClick(View view) {
        try {
            getRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,"Кнопка нажимается", Toast.LENGTH_LONG).show();
    }


    public void getRequest () throws IOException {
        new Thread(new Runnable() {
            public void run() {
                final String str = getGroupList();
            }
        }).start();
    }

    private  String getGroupList(){
        try {
            String str1 = "http://rasp.dmami.ru/groups-list.json?c107ba4058ced46cd4bb87fffdb70474";
            URL obj = new URL(str1);
            HttpURLConnection connection =(HttpURLConnection) obj.openConnection();
            connection.setRequestProperty("Referer", "http://rasp.dmami.ru/");
            connection.setRequestProperty("Accept-Charset", "UTF-8");

            InputStream response = connection.getInputStream();
            Scanner scanner = new Scanner(response).useDelimiter("\\A");
            final String s = scanner.hasNext()?scanner.next(): "";
            Log.d("String", "GetAnswer is " +s);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText(s);
                }
            });

            return s;

            }
        catch (Exception exp){
            return exp.toString();
          }
        }

}