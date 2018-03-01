package com.graduation.scheduleapp.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LessonInfo {
    @SerializedName("subject")
    private String subject;
    @SerializedName("teacher") //
    private String teacherName;
    @SerializedName("date_from")
    private String dateOfLessonStart;
    @SerializedName("date_to")
    private String dateOfLessonEnd;
    @SerializedName("auditories")
    private List<Auditoria> auditories;
    @SerializedName("type")
    private String type;
    @SerializedName("week")
    private String week;

    public String getSubject() {
        return subject;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getDateOfLessonStart() {
        return dateOfLessonStart;
    }

    public String getDateOfLessonEnd() {
        return dateOfLessonEnd;
    }

    public List<Auditoria> getAuditories() {
        return auditories;
    }

    public String getType() {
        return type;
    }

    public String getWeek() {
        return week;
    }
}