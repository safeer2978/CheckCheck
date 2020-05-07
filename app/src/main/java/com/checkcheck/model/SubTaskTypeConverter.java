package com.checkcheck.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class SubTaskTypeConverter {

        @TypeConverter
        public String fromCountryLangList(List<SubTask> subtaskList) {
            if (subtaskList == null) {
                return (null);
            }
            Gson gson = new Gson();
            Type type = new TypeToken<List<SubTask>>() {}.getType();
            String json = gson.toJson(subtaskList, type);
            return json;
        }

        @TypeConverter
        public List<SubTask> toCountryLangList(String countryLangString) {
            if (countryLangString == null) {
                return (null);
            }
            Gson gson = new Gson();
            Type type = new TypeToken<List<SubTask>>() {}.getType();
            List<SubTask> countryLangList = gson.fromJson(countryLangString, type);
            return countryLangList;
        }



}
