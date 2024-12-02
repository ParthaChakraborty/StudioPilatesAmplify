
package com.studio.amplify.pojo.feed_list;

import java.io.Serializable;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeWorkout implements Serializable, Parcelable
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
    public final static Creator<HomeWorkout> CREATOR = new Creator<HomeWorkout>() {


        @SuppressWarnings({
            "unchecked"
        })
        public HomeWorkout createFromParcel(android.os.Parcel in) {
            return new HomeWorkout(in);
        }

        public HomeWorkout[] newArray(int size) {
            return (new HomeWorkout[size]);
        }

    }
    ;
    private final static long serialVersionUID = 8967644886308712443L;

    protected HomeWorkout(android.os.Parcel in) {
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public HomeWorkout() {
    }

    /**
     * 
     * @param image
     * @param title
     * @param url
     */
    public HomeWorkout(String title, String url, String image) {
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
