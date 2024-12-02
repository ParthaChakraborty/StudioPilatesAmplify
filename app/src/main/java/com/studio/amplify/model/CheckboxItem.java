package com.studio.amplify.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class CheckboxItem implements Serializable {
    private int id;
    private String title;
    private boolean isChecked;


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
