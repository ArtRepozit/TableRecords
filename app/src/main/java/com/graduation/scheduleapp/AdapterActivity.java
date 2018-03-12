package com.graduation.scheduleapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.graduation.scheduleapp.DescriptionOfDay.SubjectDescription;

import java.util.ArrayList;
import java.util.List;

public class AdapterActivity extends BaseAdapter {

    List<SubjectDescription> lesson = new ArrayList<>();

    Context myText;


    public AdapterActivity(Context myText, List<SubjectDescription> subjectDescriptionList) {
        lesson = subjectDescriptionList;
        this.myText = myText;
    }

    @Override
    public int getCount() {
        return lesson.size();
    }

    @Override
    public Object getItem(int position) {
        return lesson.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(myText);

        if( view == null){
            view = inflater.inflate(R.layout.content_schedule_modul, viewGroup, false);
        }

        TextView dayOfWeek = (TextView) view.findViewById(R.id.text_dayOfWeek);
        TextView lessonTime = (TextView) view.findViewById(R.id.text_lessonTime);
        TextView classRoom = (TextView) view.findViewById(R.id.text_classRoom);
        TextView lessonName = (TextView) view.findViewById(R.id.text_lessonName);
        TextView teacherName = (TextView) view.findViewById(R.id.text_teacherName);
        TextView lessonLength = (TextView) view.findViewById(R.id.text_lessonLength);
        TextView lessonType= (TextView) view.findViewById(R.id.text_lessonType);

        dayOfWeek.setText(lesson.get(i).dayOfWeek);
        lessonTime.setText(lesson.get(i).lessonTime);
        classRoom.setText(lesson.get(i).classRoom);
        lessonName.setText(lesson.get(i).lessonName);
        teacherName.setText(lesson.get(i).teacherName);
        lessonLength.setText(lesson.get(i).lessonLength);
        lessonType.setText(lesson.get(i).lessonType);
        return view;
    }
}


