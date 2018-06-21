package com.graduation.scheduleapp.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;


public class ParseSession {
    @SerializedName("grid")
    //public Map<String, List<SessionInfo>> grid;
    public Map<String,Map<String, List<SessionInfo>>> grid;
}
