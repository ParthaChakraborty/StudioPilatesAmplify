
package com.studio.amplify.pojo.calender;

import java.io.Serializable;
import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CalCheckedItems implements Serializable, Parcelable
{

    @SerializedName("calender")
    @Expose
    private List<Calender> calender = null;
    public final static Creator<CalCheckedItems> CREATOR = new Creator<CalCheckedItems>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CalCheckedItems createFromParcel(android.os.Parcel in) {
            return new CalCheckedItems(in);
        }

        public CalCheckedItems[] newArray(int size) {
            return (new CalCheckedItems[size]);
        }

    }
    ;
    private final static long serialVersionUID = 2054145430199270699L;

    protected CalCheckedItems(android.os.Parcel in) {
        in.readList(this.calender, (com.studio.amplify.pojo.calender.Calender.class.getClassLoader()));
    }

    public CalCheckedItems() {
    }

    public List<Calender> getCalender() {
        return calender;
    }

    public void setCalender(List<Calender> calender) {
        this.calender = calender;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(calender);
    }

    public int describeContents() {
        return  0;
    }

}
