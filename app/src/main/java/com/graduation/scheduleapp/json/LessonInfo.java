package com.graduation.scheduleapp.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LessonInfo {
    @SerializedName("subject")
    public String subject;
    @SerializedName("teacher") //
    public String teacherName;
    @SerializedName("date_from")
    public String dateOfLessonStart;
    @SerializedName("date_to")
    public String dateOfLessonEnd;
    @SerializedName("auditories")
    public List<Auditoria> auditories;
    @SerializedName("type")
    public String type;
    @SerializedName("week")
    public String week;


}