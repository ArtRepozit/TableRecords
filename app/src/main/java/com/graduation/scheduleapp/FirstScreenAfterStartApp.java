package com.graduation.scheduleapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstScreenAfterStartApp extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    Button buAllGroup;
    Button buPintSchoolGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen_after_start_app);
        buAllGroup = (Button) findViewById(R.id.bu_all_groups);

        buAllGroup.setOnClickListener(this);

        buPintSchoolGroup = (Button) findViewById(R.id.bu_print_school);

        buPintSchoolGroup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        // Реализация перехода на экран со вводом номера группы
        intent = new Intent(FirstScreenAfterStartApp.this, SecondScreenAfterStartApp.class);
        startActivity(intent);
        switch (view.getId()) {
            case R.id.bu_all_groups:
                SaveFirstAddress();
                break;
            case R.id.bu_print_school:
                SaveFirstAddressForPrintingSchool();// код исполняемое при нажатии
                break;
        }

    }

    public void SaveFirstAddress() {
        SharedPreferences SPreference = getSharedPreferences(getString(R.string.key_shop_for_first_address), MODE_PRIVATE);
        SharedPreferences.Editor editor = SPreference.edit();
        String value1 = "http://rasp.dmami.ru/site/group?group=";
        editor.putString(getString(R.string.first_address), value1);
        editor.apply();
        Log.d("PREF", "value saving: " + value1);

        //Toast.makeText(this, "Saved first address", Toast.LENGTH_LONG).show();
    }


    public void SaveFirstAddressForPrintingSchool() {
        SharedPreferences SPreference = getSharedPreferences(getString(R.string.key_shop_for_first_address), MODE_PRIVATE);
        SharedPreferences.Editor editor = SPreference.edit();
        String value = "http://st-rasp.dmami.ru/site/group?group=";
        editor.putString(getString(R.string.first_address), value);
        editor.apply();
        Log.d("PREF", "value saving: " + value);

        //Toast.makeText(this, "Saved first address for printing school", Toast.LENGTH_LONG).show();

    }
}
