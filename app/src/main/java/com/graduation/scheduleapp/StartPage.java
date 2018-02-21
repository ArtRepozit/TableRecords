package com.graduation.scheduleapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import static android.app.PendingIntent.getActivity;

//наследование View.OnClickListener для прямой работы с интерфейсом, без создания объекта
public class StartPage extends AppCompatActivity implements View.OnClickListener {

// объявление используемых элементов
    EditText StartEdit;
    Button buSave,buLoad;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // Создание экземпляров объекта представления и их захват
        StartEdit = (EditText) findViewById(R.id.StartEdit);
        buSave =(Button) findViewById(R.id.buSave);

        //вызов обработчика нажатия на текущую(this) кнопку
            buSave.setOnClickListener(this);
        buLoad =(Button) findViewById(R.id.buLoad);
            buLoad.setOnClickListener(this);
        loadText();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buLoad:
                loadText();
                break;
            case R.id.buSave:
                textSave();
                break;
            default:
                break;
        }

    }

    public void  textSave(){
        SharedPreferences SPreference = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = SPreference.edit();
        editor.putString(getString(R.string.userName) ,StartEdit.getText().toString());//здесь возможно тоже проблемка
        editor.apply();

        Toast.makeText(this,"Saved", Toast.LENGTH_LONG).show();

    }

    public void loadText(){
        SharedPreferences SPreference = getPreferences(MODE_PRIVATE);
        String groupNumber = SPreference.getString(getString(R.string.userName),"");// здесь проблемка
        StartEdit.setText(groupNumber);

        Toast.makeText(this,"Loaded", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textSave();
    }

}
