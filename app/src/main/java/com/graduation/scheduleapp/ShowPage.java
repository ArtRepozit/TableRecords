package com.graduation.scheduleapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.graduation.scheduleapp.DescriptionOfDay.SubjectDescription;
import com.graduation.scheduleapp.DescriptionOfDay.ListDay;
import com.graduation.scheduleapp.json.Auditoria;
import com.graduation.scheduleapp.json.LessonInfo;
import com.graduation.scheduleapp.json.ParseGson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static android.media.CamcorderProfile.get;


public class ShowPage extends Activity implements View.OnClickListener {

    TextView showGet;
    Button buGet, buShowSchedule, buNextActivity;
    EditText startEd;
    Handler handler;
    ListView listView;
    ListDay listDaty;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_table);
        startEd = (EditText) findViewById(R.id.startEdit);
        showGet = (TextView) findViewById(R.id.show_get);

        buGet = (Button) findViewById(R.id.bu_get_group_list);
        buGet.setOnClickListener(this);

        buShowSchedule = (Button) findViewById(R.id.bu_show_schedule_unformat);
        buShowSchedule.setOnClickListener(this);

        buNextActivity = (Button) findViewById(R.id.bu_next_activity);
        buNextActivity.setOnClickListener(this);

        // listView = (ListView) findViewById(R.id.list_view);


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
            case R.id.bu_next_activity:
                //goToNextPage();
                getInfo(new ParseGson());
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
        Toast.makeText(this, "Кнопка нажимается", Toast.LENGTH_LONG).show();

    }

    // поток для вывода списка групп
    public void getRequestTread() throws IOException {
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

            handler.post(new Runnable() {
                @Override
                public void run() {
                    //             ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText(s);
                }
            });

            return s;

        } catch (Exception exp) {
            return exp.toString();
        }
    }
    //конец метода повыводящего список групп

    // возвращаем значение введённого пользвователем номера группы
    public String saveGroupNumber() {
        SharedPreferences SPreference = getSharedPreferences(getString(R.string.key_shop), Activity.MODE_PRIVATE);
        String enGrNum = SPreference.getString(getString(R.string.userName), "");
        return enGrNum;
    }

// gson practic

    final Gson gSON = new GsonBuilder().setPrettyPrinting().create();

    // Поток для вывода расписания
    private void getScheduleThread() throws IOException {
        new Thread(new Runnable() {
            public void run() {
                final String str = getGroupSchedule();
            }
        }).start();
    }

    public String getGroupSchedule() {
        try {
            String basicUrl;
            //Log.d("fall","num is = " + saveGroupNumber());
            basicUrl = "http://rasp.dmami.ru/site/group?group=" + saveGroupNumber();

            String fullUrl = basicUrl;
            URL obj = new URL(fullUrl);
            //Log.d("Url", "final url string is " + fullUrl);

            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestProperty("Referer", "http://rasp.dmami.ru/");
            connection.setRequestProperty("Accept-Charset", "UTF-8");

            InputStream response = connection.getInputStream();
            Scanner scanner = new Scanner(response).useDelimiter("\\A");
            final String s = scanner.hasNext() ? scanner.next() : "";
            Log.d("Schedule", "Answer is " + s);

            final ParseGson parseGson = gSON.fromJson(s, ParseGson.class);
            List<SubjectDescription> subjectDescriptions = getInfo(parseGson);
         /*

           у меня в объекте лежит структура данных в виде LinkedTreeMap которая получилась после парсинга JSON файла,
            нужен метод который будет по ключу вытаскивать значение из этого объета используя заготовленный шабло в виде класса
            он должен куда-то это значение класть


        */


            handler.post(new Runnable() {
                @Override
                public void run() {
                    //     ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText((CharSequence) dt.getOne());
                    // ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText(parseGson.toString());
                    ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText(parseGson.toString());


                }
            });

            return parseGson.toString();

        } catch (Exception exp) {
            return exp.toString();
        }
    }
    //конец метода выводящего расписание


    //
    private List<SubjectDescription> getInfo(ParseGson parseGson) {
        // Map<String,Map<String, List<LessonInfo>>> grid;
        ;
        List<SubjectDescription> list = new ArrayList<>();
        for (Map.Entry<String, Map<String, List<LessonInfo>>> entryToDay : parseGson.grid.entrySet()) {

            String dayOfWeek = "Неизвестный день недели";
            if (entryToDay.getKey().equals("1")) {
                dayOfWeek = "Понедельник";
            } else if (entryToDay.getKey().equals("2")) {
                dayOfWeek = "Вторник";
            } else if (entryToDay.getKey().equals("3")) {
                dayOfWeek = "Среда";
            } else if (entryToDay.getKey().equals("4")) {
                dayOfWeek = "Четверг";
            } else if (entryToDay.getKey().equals("5")) {
                dayOfWeek = "Пятница";
            } else if (entryToDay.getKey().equals("6")) {
                dayOfWeek = "Суббота";
            }


            for (Map.Entry<String, List<LessonInfo>> enrtyToLessons : entryToDay.getValue().entrySet()) {
                String timeOfLesson = "00:00 - 00:00";

                if (enrtyToLessons.getKey().equals("1")) {
                    timeOfLesson = "9:00 - 10:30";
                } else if (enrtyToLessons.getKey().equals("2")) {
                    timeOfLesson = "10:40 - 12:10";
                } else if (enrtyToLessons.getKey().equals("3")) {
                    timeOfLesson = "12:20 - 13:50";
                } else if (enrtyToLessons.getKey().equals("4")) {
                    timeOfLesson = "14:30 - 16:00";
                } else if (enrtyToLessons.getKey().equals("5")) {
                    timeOfLesson = "16:10 - 18:30";
                } else if (enrtyToLessons.getKey().equals("6")) {
                    timeOfLesson = "18:30 - 20:00";
                } else if (enrtyToLessons.getKey().equals("7")) {
                    timeOfLesson = "20:10 - 21:40";
                }

                for (LessonInfo lessonInfo : enrtyToLessons.getValue()) {
                    SubjectDescription subjectDescription = new SubjectDescription();
                    subjectDescription.dayOfWeek = dayOfWeek;
                    subjectDescription.lessonName = lessonInfo.subject;
                    subjectDescription.lessonTime = timeOfLesson;
                    subjectDescription.classRoom= lessonInfo.auditories.get(0).title;
                    subjectDescription.teacherName = lessonInfo.teacherName;
                    subjectDescription.lessonLength = lessonInfo.dateOfLessonStart + "  -  " + lessonInfo.dateOfLessonEnd;
                    subjectDescription.lessonType = lessonInfo.type;
                    Log.d("LN", "lesson name is " + subjectDescription.lessonName);
                    list.add(subjectDescription);
                }
            }
        }

        return list;
    }

    public void makeDayList() {
        final List<String> list = new ArrayList<String>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter);

    }

    public void goToNextPage() {
        intent = new Intent(ShowPage.this, ScheduleModul.class);
        startActivity(intent);
    }
}