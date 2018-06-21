package com.graduation.scheduleapp.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class SessionInfo {
    @SerializedName("subject")
    public String subject;
    @SerializedName("teacher")
    public String teacher;
    @SerializedName("auditories")
    public List<Auditoria> auditories;
    @SerializedName("type")
    public String type;
}
