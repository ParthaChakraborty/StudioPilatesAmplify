package com.studio.amplify.model;

import java.util.ArrayList;

public class CalendarEventItem {

    public ArrayList<String> idList;
    public String idString;
    String date;

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public ArrayList<String> getIdList() {
        return idList;
    }

    public void setIdList(ArrayList<String> idList) {
        this.idList = idList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CalendarEventItem{" +
                "date='" + date + '\'' +
                '}';
    }
}

