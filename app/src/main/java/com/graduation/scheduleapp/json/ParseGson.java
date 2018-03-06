package com.graduation.scheduleapp.json;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ParseGson {
    @SerializedName("grid")
    public Map<String,Map<String, List<LessonInfo>>> grid;

}

