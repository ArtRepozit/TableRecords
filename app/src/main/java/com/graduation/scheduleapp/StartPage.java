package com.graduation.scheduleapp;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.graduation.scheduleapp.jsonForGiveGrupList.ParseGroupList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static android.app.PendingIntent.getActivity;

//наследование View.OnClickListener для прямой работы с интерфейсом, без создания объекта
public class StartPage extends AppCompatActivity implements View.OnClickListener {


    // объявление используемых элементов
    EditText startEdit;
    Button buSave;
    Intent intent;
//   для парсинга даных
    final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);// Привязка класса к layout
        // Создание экземпляров объекта представления и их захват
        startEdit = (EditText) findViewById(R.id.startEdit);
        buSave = (Button) findViewById(R.id.buSave);
        //вызов обработчика нажатия на текущую(this) кнопку
        buSave.setOnClickListener(this);
        loadText();
      /*  try {

            getRequestTread();
        } catch (IOException e) {

            e.printStackTrace();
        }
*/

    }

    @Override
    public void onClick(View view) {
        //вызов метода сохранения текста при нажатии на кнопку
        textSave();
        // Реализации перехода на новую активити
        intent = new Intent(StartPage.this, ShowPage.class);/*Похоже, что сначала говорим, мол это
        текущий класс описания, а после нажатия перейди в этот.*/
        startActivity(intent);
    }

    public void textSave() {
        SharedPreferences SPreference = getSharedPreferences(getString(R.string.key_shop), MODE_PRIVATE);
        SharedPreferences.Editor editor = SPreference.edit();
        String value = startEdit.getText().toString();
        editor.putString(getString(R.string.userName), value);
        editor.apply();
        Log.d("PREF", "value saving: " + value);

       // Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();

    }

    public void loadText() {

        SharedPreferences SPreference = getSharedPreferences(getString(R.string.key_shop), Activity.MODE_PRIVATE);
        String groupNumber = SPreference.getString(getString(R.string.userName), "");
        startEdit.setText(groupNumber);
       // Toast.makeText(this, "Loaded", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textSave();
    }

    @Override
    protected void onStop() {
        super.onStop();
        textSave();
    }


    // поток для вывода списка групп всех форма обучения
 /*   public void getListTread() throws IOException {
        new Thread(new Runnable() {
            public void run() {
                final String str = getGroupList();
            }
        }).start();
    }

    private String getGroupList() {
        try {
            String str1 = "http://rasp.dmami.ru/groups-list.json?c107ba4058ced46cd4bb87fffdb70474";
            URL obj = new URL(str1);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestProperty("Referer", "http://rasp.dmami.ru/");
            connection.setRequestProperty("Accept-Charset", "UTF-8");

            InputStream response = connection.getInputStream();
            Scanner scanner = new Scanner(response).useDelimiter("\\A");
            final String s = scanner.hasNext() ? scanner.next() : "";
            Log.d("String", "GetAnswer is " + s);

            final ParseGroupList groupList  = gson.fromJson(s, ParseGroupList.class);
            Log.d("groupList", "Groups is + " + groupList);
           /* handler.post(new Runnable() {
                @Override
                public void run() {
                    //             ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText(s);
                }
            });
            * / //
            return s;

        } catch (Exception exp) {
            return exp.toString();
        }
    }
    */

}
