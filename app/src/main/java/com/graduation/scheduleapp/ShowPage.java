package com.graduation.scheduleapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.graduation.scheduleapp.DescriptionOfDay.SessionDecription;
import com.graduation.scheduleapp.DescriptionOfDay.SubjectDescription;
import com.graduation.scheduleapp.json.LessonInfo;
import com.graduation.scheduleapp.json.ParseGson;
import com.graduation.scheduleapp.json.ParseSession;
import com.graduation.scheduleapp.json.SessionInfo;
import com.graduation.scheduleapp.jsonForGiveGrupList.ParseGroupList;

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

    Inference inference;
    Button buGet;
    Button buShowSchedule;
    Button buHSP;
    EditText startEd;
    Handler handler;
    ListView listView;
    AdapterActivity adapterActivity;
    AdapterForSession adapterForSession;
    Intent intent;

    String firstAddress;


    //FirstScreenAfterStartApp firstScreenAfterStartApp = new FirstScreenAfterStartApp();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_table);
        startEd = (EditText) findViewById(R.id.startEdit);


        buGet = (Button) findViewById(R.id.bu_get_group_list);
        buGet.setOnClickListener(this);

        buShowSchedule = (Button) findViewById(R.id.bu_show_schedule_unformat);
        buShowSchedule.setOnClickListener(this);

        buHSP = (Button) findViewById(R.id.bu_show_HSP);
        buHSP.setOnClickListener(this);

        //listView = (ListView) findViewById(R.id.list_view);

        inference = new Inference();
        handler = new Handler();

        SelectThread();
    }

    //метод загрузки потока
    public void SelectThread() {
        // присвоение адресса в строковую переменную
        firstAddress = saveFirstAddress();

        switch (firstAddress) {
            case "http://rasp.dmami.ru/site/group?group=&session=0":
                try {
                    getScheduleThread();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "http://rasp.dmami.ru/site/group?group=&session=1":

                try {
                    getScheduleThread2();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "http://st-rasp.dmami.ru/site/group?group=&session=0":
                try {
                    startSecondThread();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "http://st-rasp.dmami.ru/site/group?group=&session=1":
                try {
                    getScheduleThread3();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    // блок загрузки ссылок получаемых из переходов по экранам

    public String saveFirstAddress() {
        SharedPreferences SPreference = getSharedPreferences(getString(R.string.key_shop_for_first_address), Activity.MODE_PRIVATE);
        String firstAddress = SPreference.getString(getString(R.string.first_address), "");
        return firstAddress;
    }


    public void onClick(View view) {
        // в блоке try мы запускаем поток(thread)
        switch (view.getId()) {
            case R.id.bu_get_group_list:
                try {
                    getScheduleThread2();
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
            case R.id.bu_show_HSP:
                try {
                    getRequestTread();
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
        Toast.makeText(this, "Кнопка нажимается", Toast.LENGTH_LONG).show();

    }

// Поток для вывода рассписания сессия Высшей Школы Печати
    private void getScheduleThread3() throws IOException {
        new Thread(new Runnable() {
            public void run() {
                final String str = getGroupSchedule3();
            }
        }).start();
    }

    public String getGroupSchedule3() {
        try {
            String basicUrl;
            //Log.d("fall","num is = " + saveGroupNumber());
            basicUrl = "http://st-rasp.dmami.ru/site/group?group=" + saveGroupNumber() + "&session=1";
            //String  bu;
            // bu = "http://rasp.dmami.ru/site/group?group=171-441&session=1";
            String fullUrl = basicUrl;
            URL obj = new URL(fullUrl);
            //Log.d("Url", "final url string is " + fullUrl);

            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Referer", "http://st-rasp.dmami.ru/session");
            connection.setRequestProperty("Accept-Charset", "UTF-8");

            InputStream response = connection.getInputStream();
            Scanner scanner = new Scanner(response).useDelimiter("\\A");
            final String s = scanner.hasNext() ? scanner.next() : "";
            Log.d("Schedule", "Answer is " + s);

            final ParseSession parseSession = gSON.fromJson(s, ParseSession.class);
            Log.d("Schedule", "Answer is " + s);

            final List<SessionDecription> sessionDecriptions = getInfo2(parseSession);


            handler.post(new Runnable() {
                @Override
                public void run() {
                    //     ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText((CharSequence) dt.getOne());
                    // ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText(parseGson.toString());
                    listView = (ListView) findViewById(R.id.list_view);
                    adapterForSession = new AdapterForSession(ShowPage.this, sessionDecriptions);
                    listView.setAdapter(adapterForSession);


                }
            });

            return sessionDecriptions.toString();

        } catch (Exception exp) {
            return exp.toString();
        }
    }

    final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    // поток вывода рассписания сессии для всех форма обучения

    // разбор значений для сессии
    private List<SessionDecription> getInfo2(ParseSession parseSession) {
        String lastDay = "";
        String monthName = "";
        String examDate;
        List<SessionDecription> list = new ArrayList<>();
        for (Map.Entry<String, Map<String, List<SessionInfo>>> entryToDay : parseSession.grid.entrySet()) {

            String date = entryToDay.getKey();
            String monthNumber = date.substring(5, 7);

            switch (monthNumber) {
                case "05":
                    monthName = "Мая";
                    break;
                case "06":
                    monthName = "Июня";
                    break;

                case "07":
                    monthName = "Июля";
                    break;
                default:
                    break;
            }
            examDate = date.substring(8,10) + " " + monthName;

            for (Map.Entry<String, List<SessionInfo>> enrtyToLessons : entryToDay.getValue().entrySet()) {

                String time = "00:00 - 00:00";

                if (enrtyToLessons.getKey().equals("1")) {
                    time = "9:00 - 10:30";
                } else if (enrtyToLessons.getKey().equals("2")) {
                    time = "10:40 - 12:10";
                } else if (enrtyToLessons.getKey().equals("3")) {
                    time = "12:20 - 13:50";
                } else if (enrtyToLessons.getKey().equals("4")) {
                    time = "14:30 - 16:00";
                } else if (enrtyToLessons.getKey().equals("5")) {
                    time = "16:10 - 18:30";
                } else if (enrtyToLessons.getKey().equals("6")) {
                    time = "18:30 - 20:00";
                } else if (enrtyToLessons.getKey().equals("7")) {
                    time = "20:10 - 21:40";
                }

                for (SessionInfo sessionInfo : enrtyToLessons.getValue()) {
                    SessionDecription sessionDecription = new SessionDecription();


                    if (!lastDay.equals(date)) {
                        sessionDecription.date = examDate;
                        lastDay = date;
                    }

                    sessionDecription.subject = "Предмет:" + "\n" +sessionInfo.subject + "\n";
                    sessionDecription.teacher ="Преподаватель:" + "\n" + sessionInfo.teacher + "\n";
                    sessionDecription.auditories = "Аудитория:" + "\n"+ sessionInfo.auditories.get(0).title + "\n";
                    sessionDecription.time ="Время:" + "\n" + time + "\n";
                    sessionDecription.type ="Тип:" + "\n" + sessionInfo.type + "\n";


                    list.add(sessionDecription);
                }
            }
        }

        return list;
    }


    private void getScheduleThread2() throws IOException {
        new Thread(new Runnable() {
            public void run() {
                final String str = getGroupSchedule2();
            }
        }).start();
    }

    public String getGroupSchedule2() {
        try {
            String basicUrl;
            //Log.d("fall","num is = " + saveGroupNumber());
            basicUrl = "http://rasp.dmami.ru/site/group?group=" + saveGroupNumber() + "&session=1";
            //String  bu;
            // bu = "http://rasp.dmami.ru/site/group?group=171-441&session=1";
            String fullUrl = basicUrl;
            URL obj = new URL(fullUrl);
            //Log.d("Url", "final url string is " + fullUrl);

            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Referer", "http://rasp.dmami.ru/session");
            connection.setRequestProperty("Accept-Charset", "UTF-8");

            InputStream response = connection.getInputStream();
            Scanner scanner = new Scanner(response).useDelimiter("\\A");
            final String s = scanner.hasNext() ? scanner.next() : "";
            Log.d("Schedule", "Answer is " + s);

            final ParseSession parseSession = gSON.fromJson(s, ParseSession.class);
            Log.d("Schedule", "Answer is " + s);

            final List<SessionDecription> sessionDecriptions = getInfo2(parseSession);


            handler.post(new Runnable() {
                @Override
                public void run() {
                    //     ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText((CharSequence) dt.getOne());
                    // ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText(parseGson.toString());
                    listView = (ListView) findViewById(R.id.list_view);
                    adapterForSession = new AdapterForSession(ShowPage.this, sessionDecriptions);
                    listView.setAdapter(adapterForSession);


                }
            });

            return sessionDecriptions.toString();

        } catch (Exception exp) {
            return exp.toString();
        }
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

            final ParseGroupList groupList = gson.fromJson(s, ParseGroupList.class);
            Log.d("groupList", "Groups is + " + groupList);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // inference.allGroup = ((ArrayList<String>))groupList.clone();
                    //          ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText(s);
                }
            });

            return s;

        } catch (Exception exp) {
            return exp.toString();
        }
    }


    // возвращаем значение введённого пользвователем номера группы
    public String saveGroupNumber() {
        SharedPreferences SPreference = getSharedPreferences(getString(R.string.key_shop), Activity.MODE_PRIVATE);
        String enGrNum = SPreference.getString(getString(R.string.userName), "");
        return enGrNum;
    }

//  объект класса Gson для работы с библиотекой gson

    final Gson gSON = new GsonBuilder().setPrettyPrinting().create();

    // Поток для вывода расписания для всех групп
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
            final List<SubjectDescription> subjectDescriptions = getInfo(parseGson);


            handler.post(new Runnable() {
                @Override
                public void run() {
                    //     ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText((CharSequence) dt.getOne());
                    // ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText(parseGson.toString());
                    listView = (ListView) findViewById(R.id.list_view);
                    adapterActivity = new AdapterActivity(ShowPage.this, subjectDescriptions);
                    listView.setAdapter(adapterActivity);


                }
            });






            /*

           у меня в объекте лежит структура данных в виде LinkedTreeMap которая получилась после парсинга JSON файла,
            нужен метод который будет по ключу вытаскивать значение из этого объета используя заготовленный шаблон в виде класса
            он должен куда-то это значение класть


        */

/*
            handler.post(new Runnable() {
              @Override
                public void run() {
                    //     ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText((CharSequence) dt.getOne());
                    // ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText(parseGson.toString());
                    ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText(subjectDescriptions.toString());


                }
            });*/


            return subjectDescriptions.toString();

        } catch (Exception exp) {
            return exp.toString();
        }
    }


    // Получаем информацию из SubjectDescription для расписания занятий
    private List<SubjectDescription> getInfo(ParseGson parseGson) {

        String lastDay = "";

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


                    if (!lastDay.equals(dayOfWeek)) {
                        subjectDescription.dayOfWeek = dayOfWeek;
                        lastDay = dayOfWeek;
                    }

                    subjectDescription.lessonName = lessonInfo.subject;
                    subjectDescription.lessonTime = timeOfLesson;
                    subjectDescription.classRoom = lessonInfo.auditories.get(0).title;
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


    // Поток для вывода расписания ВШП
    private void startSecondThread() throws IOException {
        new Thread(new Runnable() {
            public void run() {
                final String str = getGroupScheduleVSP();
            }
        }).start();
    }

    public String getGroupScheduleVSP() {
        try {
            String basicUrl;
            //Log.d("fall","num is = " + saveGroupNumber());
            basicUrl = "http://st-rasp.dmami.ru/site/group?group=" + saveGroupNumber();

            String fullUrlVSP = basicUrl;
            URL obj = new URL(fullUrlVSP);
            //Log.d("Url", "final url string is " + fullUrl);

            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestProperty("Referer", "http://st-rasp.dmami.ru/");
            connection.setRequestProperty("Accept-Charset", "UTF-8");

            InputStream response = connection.getInputStream();
            Scanner scanner = new Scanner(response).useDelimiter("\\A");
            final String s = scanner.hasNext() ? scanner.next() : "";
            Log.d("Schedule", "Answer is " + s);

            final ParseGson parseGson = gSON.fromJson(s, ParseGson.class);
            final List<SubjectDescription> subjectDescriptions = getInfo(parseGson);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    //     ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText((CharSequence) dt.getOne());
                    // ((TextView) ShowPage.this.findViewById(R.id.show_get)).setText(parseGson.toString());
                    listView = (ListView) findViewById(R.id.list_view);
                    adapterActivity = new AdapterActivity(ShowPage.this, subjectDescriptions);
                    listView.setAdapter(adapterActivity);


                }
            });

            return subjectDescriptions.toString();


        } catch (Exception exp) {
            return exp.toString();
        }
    }

    public void goToNextPage() {
        intent = new Intent(ShowPage.this, AdapterActivity.class);
        startActivity(intent);
    }
}