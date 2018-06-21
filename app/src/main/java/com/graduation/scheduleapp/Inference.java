package com.graduation.scheduleapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Inference extends AppCompatActivity {
/*
* 1. Создать метод для загрузки сохранённого номера группы.
* 2. Получить список групп для всех форм обучения.
* 3. Получить список групп для ВШП.
*  Это можно делать сразу призапуске приложения, пока пользовательно будет думать как ввести номер группы.
* 4. Узнать к какому списку относиться текст , полученный в методе загрузки сохр. номера группы.
* 5. Обработку этого метода составить в блоке try/catch(приорететно)  if/else.
* 6. В случае, если значение группы совподает со значением одного из списков, заускать поток получения расписания,
    * соответсвующий значению группы
* 7. Помним, что может быть введён некоректный номер группы, об этом нужно сообщать пользователю*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inference);



    }

// Получаем номер группы введённый пользователем на стартовой странице
    public String getGroupNumber() {
        SharedPreferences SPreference = getSharedPreferences(getString(R.string.key_shop), Activity.MODE_PRIVATE);
        String enGrNum = SPreference.getString(getString(R.string.userName), "");
        return enGrNum;
    }


    // Пробуем вписать в ArrayList значения полученные в результате парсинга

    public List<String> allGroup;

}
