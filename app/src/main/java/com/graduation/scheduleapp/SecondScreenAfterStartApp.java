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

public class SecondScreenAfterStartApp extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    Button buLessons;
    Button buSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen_after_start_app);
        buLessons = (Button) findViewById(R.id.bu_lessons);

        buLessons.setOnClickListener(this);

        buSession = (Button) findViewById(R.id.bu_session);

        buSession.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        intent = new Intent(SecondScreenAfterStartApp.this, StartPage.class);
        startActivity(intent);
        switch (view.getId()){
            case R.id.bu_lessons:
                SaveSecondAddressForLesson();
                break;
            case R.id.bu_session:
                SaveSecondAddressForSession();// код исполняемое при нажатии
                break;
        }
}
// метод отслеживания рассписания занятий
    public void SaveSecondAddressForLesson() {
        SharedPreferences SPreference = getSharedPreferences(getString(R.string.key_shop_for_second_address), MODE_PRIVATE);
        SharedPreferences.Editor editor = SPreference.edit();
        String value2 = saveFirstAddress() + "&session=0";
        editor.putString(getString(R.string.second_address_for_lessons), value2);
        editor.apply();
        Log.d("PREF", "value saving: " + value2);

    //    Toast.makeText(this, "Saved first address for lessons", Toast.LENGTH_LONG).show();
    }

    public String saveFirstAddress() {
        SharedPreferences SPreference = getSharedPreferences(getString(R.string.key_shop_for_first_address), Activity.MODE_PRIVATE);
        String firstAddress = SPreference.getString(getString(R.string.first_address), "");
        return firstAddress;
    }
    // метод отслеживания рассписания сессии
    public void SaveSecondAddressForSession() {
        SharedPreferences SPreference = getSharedPreferences(getString(R.string.key_shop_for_second_address), MODE_PRIVATE);
        SharedPreferences.Editor editor = SPreference.edit();
        String value = saveFirstAddress() + "&session=1";
        editor.putString(getString(R.string.second_address_for_lessons), value);
        editor.apply();
        Log.d("PREF", "value saving: " + value);

       // Toast.makeText(this, "Saved first address for session", Toast.LENGTH_LONG).show();

    }


}
