package com.graduation.scheduleapp.DescriptionOfDay;

import com.graduation.scheduleapp.json.Auditoria;
import com.graduation.scheduleapp.json.LessonInfo;


import java.util.List;



public class SubjectDescription {
    public String dayOfWeek;
    public String lessonTime;
    //public Auditoria classRoom;
    public String  classRoom;
    public String lessonName;
    public String teacherName;
    public String lessonLength;
    public String lessonType;

    @Override
    public String toString() {
        return "SubjectDescription{" +
                "dayOfWeek='" + dayOfWeek + '\'' +
                ", lessonTime='" + lessonTime + '\'' +
                ", classRoom='" + classRoom + '\'' +
                ", lessonName='" + lessonName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", lessonLength='" + lessonLength + '\'' +
                ", lessonType='" + lessonType + '\'' +
                '}';
    }
}
