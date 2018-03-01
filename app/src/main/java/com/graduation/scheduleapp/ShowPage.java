package com.graduation.scheduleapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.graduation.scheduleapp.json.ParseGson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ShowPage extends Activity implements View.OnClickListener{

    TextView showGet;
    Button buGet, buShowSchedule;
    EditText startEd;
    Handler handler;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_table);
        startEd = (EditText) findViewById(R.id.startEdit);
        showGet = (TextView) findViewById(R.id.show_get);
        buGet = (Button) findViewById(R.id.bu_get_group_list);
            buGet.setOnClickListener(this);

        buShowSchedule = (Button) findViewById(R.id.bu_show_schedule_unformat);
            buShowSchedule.setOnClickListener(this);

        handler = new Handler();
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bu_get_group_list:
                try {
                    getRequestTread();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                    break;
            case R.id.bu_show_schedule_unformat:
                try {
                    getScheduleThread();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                    break;
            default:
                    break;
        }
       /* String s1 = getString(R.string.userName);
        Log.d("take num", " Entering string is " + s1);*/
        //Context context = getApplicationContext();

      //  SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.userName),MODE_PRIVATE);
       // String groupNum = SharedPreferences.;
      /*  SharedPreferences SPreference = getSharedPreferences(getString(R.string.key_shop), Activity.MODE_PRIVATE);
        String groupNum = SPreference.getString(getString(R.string.userName),"");
        Log.d("fall","num is = " + groupNum);
*/
        Toast.makeText(this,"Кнопка нажимается", Toast.LENGTH_LONG).show();

    }

    // поток для вывода списка групп
    public void getRequestTread () throws IOException {
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
    //конец метода повыводящего список групп

    // возвращаем значение введённого пользвователем номера группы
    public String saveGroupNumber(){
        SharedPreferences SPreference = getSharedPreferences(getString(R.string.key_shop), Activity.MODE_PRIVATE);
           String enGrNum = SPreference.getString(getString(R.string.userName),"");
           return  enGrNum;
    }

// gson practic

//    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Поток для вывода расписания
    public void getScheduleThread () throws IOException {
        new Thread(new Runnable() {
            public void run() {
                final String str = getGroupSchedule();
            }
        }).start();
    }

    private  String getGroupSchedule(){
        try {
            String  basicUrl;
            //Log.d("fall","num is = " + saveGroupNumber());
            basicUrl = "http://rasp.dmami.ru/site/group?group=" + saveGroupNumber();

            String fullUrl = basicUrl;
            URL obj = new URL(fullUrl);
            //Log.d("Url", "final url string is " + fullUrl);

            HttpURLConnection connection =(HttpURLConnection) obj.openConnection();
            connection.setRequestProperty("Referer", "http://rasp.dmami.ru/");
            connection.setRequestProperty("Accept-Charset", "UTF-8");

            InputStream response = connection.getInputStream();
            Scanner scanner = new Scanner(response).useDelimiter("\\A");
            final String s = scanner.hasNext()?scanner.next(): "";
            Log.d("Schedule", "Answer is " +s);

            //final DataParse dt = GSON.fromJson(s,DataParse.class);
            final Gson gSON = new GsonBuilder().setPrettyPrinting().create();
            final ParseGson parseGson = gSON.fromJson(s, ParseGson.class);
            handler.post(new Runnable() {
                @Override
                public void run() {
               //     ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText((CharSequence) dt.getOne());
                  ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText(parseGson.toString());


                }
            });

            return s;

        }
        catch (Exception exp){
            return exp.toString();
        }
    }
    //конец метода выводящего расписание

}