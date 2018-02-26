package com.graduation.scheduleapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import static android.app.PendingIntent.getActivity;

//наследование View.OnClickListener для прямой работы с интерфейсом, без создания объекта
public class StartPage extends AppCompatActivity implements View.OnClickListener {


    // объявление используемых элементов
    EditText startEdit;
    Button buSave,buLoad;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);// Привязка класса к layout
        // Создание экземпляров объекта представления и их захват
        startEdit = (EditText) findViewById(R.id.StartEdit);
        buSave =(Button) findViewById(R.id.buSave);
        //вызов обработчика нажатия на текущую(this) кнопку
            buSave.setOnClickListener(this);
        loadText();
    }

    @Override
    public void onClick(View view) {
        textSave();
        intent = new Intent(StartPage.this, ShowPage.class);
        startActivity(intent);
    }

    public void  textSave(){
        SharedPreferences SPreference = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = SPreference.edit();
        String value = startEdit.getText().toString();
        editor.putString(getString(R.string.userName), value);
        editor.apply();
      //  Log.d("PREF", "value saving: " + value);

        Toast.makeText(this,"Saved", Toast.LENGTH_LONG).show();

    }

    public void loadText(){
        SharedPreferences SPreference = getPreferences(MODE_PRIVATE);
        String groupNumber = SPreference.getString(getString(R.string.userName),"");
        startEdit.setText(groupNumber);

        Toast.makeText(this,"Loaded", Toast.LENGTH_LONG).show();
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

}
