
package com.studio.amplify.pojo.calender;

import java.io.Serializable;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Calender implements Serializable, Parcelable
{

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("ids")
    @Expose
    private String ids;
    public final static Creator<Calender> CREATOR = new Creator<Calender>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Calender createFromParcel(android.os.Parcel in) {
            return new Calender(in);
        }

        public Calender[] newArray(int size) {
            return (new Calender[size]);
        }

    }
    ;
    private final static long serialVersionUID = -5002950031940609958L;

    protected Calender(android.os.Parcel in) {
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.ids = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Calender() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(date);
        dest.writeValue(ids);
    }

    public int describeContents() {
        return  0;
    }

}
