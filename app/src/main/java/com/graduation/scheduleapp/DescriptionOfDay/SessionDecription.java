package com.graduation.scheduleapp.DescriptionOfDay;



public class SessionDecription {
    public String date;
    public String subject;
    public String teacher;
    public String type;
    public String auditories;
    public String time;

    @Override
    public String toString() {
        return "SessionDecription{" +
                "date='" + date + '\'' +
                ", subject='" + subject + '\'' +
                ", teacher='" + teacher + '\'' +
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                ", auditories='" + auditories + '\'' +
                '}';
    }
}
