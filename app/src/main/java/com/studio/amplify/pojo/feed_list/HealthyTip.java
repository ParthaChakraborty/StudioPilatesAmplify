
package com.studio.amplify.pojo.feed_list;

import java.io.Serializable;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthyTip implements Serializable, Parcelable
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
    @SerializedName("post_date")
    @Expose
    private String postDate;
    public final static Creator<HealthyTip> CREATOR = new Creator<HealthyTip>() {


        @SuppressWarnings({
            "unchecked"
        })
        public HealthyTip createFromParcel(android.os.Parcel in) {
            return new HealthyTip(in);
        }

        public HealthyTip[] newArray(int size) {
            return (new HealthyTip[size]);
        }

    }
    ;
    private final static long serialVersionUID = 855934870724446305L;

    protected HealthyTip(android.os.Parcel in) {
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.postDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public HealthyTip() {
    }

    /**
     * 
     * @param image
     * @param postDate
     * @param title
     * @param url
     */
    public HealthyTip(String title, String url, String image, String postDate) {
        super();
        this.title = title;
        this.url = url;
        this.image = image;
        this.postDate = postDate;
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

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(url);
        dest.writeValue(image);
        dest.writeValue(postDate);
    }

    public int describeContents() {
        return  0;
    }

}
