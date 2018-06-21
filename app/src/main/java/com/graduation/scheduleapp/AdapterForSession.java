package com.graduation.scheduleapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.graduation.scheduleapp.DescriptionOfDay.SessionDecription;


import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class AdapterForSession extends BaseAdapter {
    List<SessionDecription> session = new ArrayList<>();

    Context myText;

    public AdapterForSession(Context myText, List<SessionDecription> sessionDecriptionList) {
        session = sessionDecriptionList;
        this.myText = myText;
    }

    @Override
    public int getCount() {
        return session.size();
    }

    @Override
    public Object getItem(int position) {
        return session.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(myText);

        if (view == null) {
            view = inflater.inflate(R.layout.content_session_module, viewGroup, false);
        }

        TextView date = (TextView) view.findViewById(R.id.text_date);
        TextView time = (TextView) view.findViewById(R.id.text_time);
        TextView subject = (TextView) view.findViewById(R.id.text_subject);
        TextView teacher = (TextView) view.findViewById(R.id.text_teacher);
        TextView type = (TextView) view.findViewById(R.id.text_type);
        TextView auditories = (TextView) view.findViewById(R.id.text_auditories);



            date.setText(session.get(i).date);
        // dayOfWeek.setText(lesson.get(i).dayOfWeek);
        if (session.get(i).date == null || session.get(i).date.isEmpty()) {
            date.setVisibility(GONE);
        } else {
            date.setVisibility(VISIBLE);
            date.setText(session.get(i).date);
        }
        time.setText(session.get(i).time);
        subject.setText(session.get(i).subject);
        teacher.setText(session.get(i).teacher);
        type.setText(session.get(i).type);
        auditories.setText(session.get(i).auditories);

        return view;
    }
}
