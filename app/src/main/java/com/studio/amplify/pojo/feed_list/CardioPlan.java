
package com.studio.amplify.pojo.feed_list;

import java.io.Serializable;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardioPlan implements Serializable, Parcelable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private String image;
    public final static Creator<CardioPlan> CREATOR = new Creator<CardioPlan>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CardioPlan createFromParcel(android.os.Parcel in) {
            return new CardioPlan(in);
        }

        public CardioPlan[] newArray(int size) {
            return (new CardioPlan[size]);
        }

    }
    ;
    private final static long serialVersionUID = 6722866985129208409L;

    protected CardioPlan(android.os.Parcel in) {
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public CardioPlan() {
    }

    /**
     * 
     * @param image
     * @param title
     * @param url
     */
    public CardioPlan(String title, String url, String image) {
        super();
        this.title = title;
        this.url = url;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(url);
        dest.writeValue(image);
    }

    public int describeContents() {
        return  0;
    }

}
