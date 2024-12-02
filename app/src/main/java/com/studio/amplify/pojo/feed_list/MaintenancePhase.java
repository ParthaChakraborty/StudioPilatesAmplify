
package com.studio.amplify.pojo.feed_list;

import java.io.Serializable;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaintenancePhase implements Serializable, Parcelable
{

    @SerializedName("start_body_weight")
    @Expose
    private String startBodyWeight;
    @SerializedName("current_body_weight")
    @Expose
    private String currentBodyWeight;
    @SerializedName("goal_weight")
    @Expose
    private String goalWeight;
    @SerializedName("start_body_fat")
    @Expose
    private String startBodyFat;
    @SerializedName("current_body_fat")
    @Expose
    private String currentBodyFat;
    @SerializedName("goal_body_fat")
    @Expose
    private String goalBodyFat;
    public final static Creator<MaintenancePhase> CREATOR = new Creator<MaintenancePhase>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MaintenancePhase createFromParcel(android.os.Parcel in) {
            return new MaintenancePhase(in);
        }

        public MaintenancePhase[] newArray(int size) {
            return (new MaintenancePhase[size]);
        }

    }
    ;
    private final static long serialVersionUID = 7868885861583561599L;

    protected MaintenancePhase(android.os.Parcel in) {
        this.startBodyWeight = ((String) in.readValue((String.class.getClassLoader())));
        this.currentBodyWeight = ((String) in.readValue((String.class.getClassLoader())));
        this.goalWeight = ((String) in.readValue((String.class.getClassLoader())));
        this.startBodyFat = ((String) in.readValue((String.class.getClassLoader())));
        this.currentBodyFat = ((String) in.readValue((String.class.getClassLoader())));
        this.goalBodyFat = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public MaintenancePhase() {
    }

    /**
     * 
     * @param goalBodyFat
     * @param startBodyWeight
     * @param currentBodyWeight
     * @param startBodyFat
     * @param goalWeight
     * @param currentBodyFat
     */
    public MaintenancePhase(String startBodyWeight, String currentBodyWeight, String goalWeight, String startBodyFat, String currentBodyFat, String goalBodyFat) {
        super();
        this.startBodyWeight = startBodyWeight;
        this.currentBodyWeight = currentBodyWeight;
        this.goalWeight = goalWeight;
        this.startBodyFat = startBodyFat;
        this.currentBodyFat = currentBodyFat;
        this.goalBodyFat = goalBodyFat;
    }

    public String getStartBodyWeight() {
        return startBodyWeight;
    }

    public void setStartBodyWeight(String startBodyWeight) {
        this.startBodyWeight = startBodyWeight;
    }

    public String getCurrentBodyWeight() {
        return currentBodyWeight;
    }

    public void setCurrentBodyWeight(String currentBodyWeight) {
        this.currentBodyWeight = currentBodyWeight;
    }

    public String getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(String goalWeight) {
        this.goalWeight = goalWeight;
    }

    public String getStartBodyFat() {
        return startBodyFat;
    }

    public void setStartBodyFat(String startBodyFat) {
        this.startBodyFat = startBodyFat;
    }

    public String getCurrentBodyFat() {
        return currentBodyFat;
    }

    public void setCurrentBodyFat(String currentBodyFat) {
        this.currentBodyFat = currentBodyFat;
    }

    public String getGoalBodyFat() {
        return goalBodyFat;
    }

    public void setGoalBodyFat(String goalBodyFat) {
        this.goalBodyFat = goalBodyFat;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(startBodyWeight);
        dest.writeValue(currentBodyWeight);
        dest.writeValue(goalWeight);
        dest.writeValue(startBodyFat);
        dest.writeValue(currentBodyFat);
        dest.writeValue(goalBodyFat);
    }

    public int describeContents() {
        return  0;
    }

}
