
package com.studio.amplify.pojo.feed_list;

import java.io.Serializable;
import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedList implements Serializable, Parcelable
{

    @SerializedName("group_id")
    @Expose
    private Object groupId;
    @SerializedName("group_name")
    @Expose
    private Object groupName;
    @SerializedName("Sdate")
    @Expose
    private String sdate;
    @SerializedName("edate")
    @Expose
    private String edate;
    @SerializedName("camchall_id")
    @Expose
    private String camchallId;
    @SerializedName("camchall_status")
    @Expose
    private String camchallStatus;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("participate")
    @Expose
    private Integer participate;
    @SerializedName("days_remaining")
    @Expose
    private String daysRemaining;
    @SerializedName("banner_text")
    @Expose
    private String bannerText;
    @SerializedName("next_challange")
    @Expose
    private String nextChallange;
    @SerializedName("maintenance_phase")
    @Expose
    private MaintenancePhase maintenancePhase;
    @SerializedName("user_group_id")
    @Expose
    private String userGroupId;
    @SerializedName("user_group_name")
    @Expose
    private String userGroupName;
    @SerializedName("dashboard_banner")
    @Expose
    private String dashboardBanner;
    @SerializedName("image_under_date")
    @Expose
    private String imageUnderDate;
    @SerializedName("healthy_tips")
    @Expose
    private List<HealthyTip> healthyTips = null;
    @SerializedName("home_workouts")
    @Expose
    private List<HomeWorkout> homeWorkouts = null;
    @SerializedName("cardio_plans")
    @Expose
    private List<CardioPlan> cardioPlans = null;
    @SerializedName("partner_offers")
    @Expose
    private List<PartnerOffer> partnerOffers = null;
    @SerializedName("is_disabled")
    @Expose
    private String isDisabled;
    @SerializedName("week_no")
    @Expose
    private String weekNo;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("current_date")
    @Expose
    private String currentDate;
    @SerializedName("health_tips_banner")
    @Expose
    private String healthTipsBanner;
    @SerializedName("home_workouts_banner")
    @Expose
    private String homeWorkoutsBanner;
    @SerializedName("cardio_plans_banner")
    @Expose
    private String cardioPlansBanner;
    @SerializedName("partner_offers_banner")
    @Expose
    private String partnerOffersBanner;
    public final static Creator<FeedList> CREATOR = new Creator<FeedList>() {


        @SuppressWarnings({
            "unchecked"
        })
        public FeedList createFromParcel(android.os.Parcel in) {
            return new FeedList(in);
        }

        public FeedList[] newArray(int size) {
            return (new FeedList[size]);
        }

    }
    ;
    private final static long serialVersionUID = -6154765377232912819L;

    protected FeedList(android.os.Parcel in) {
        this.groupId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.groupName = ((Object) in.readValue((Object.class.getClassLoader())));
        this.sdate = ((String) in.readValue((String.class.getClassLoader())));
        this.edate = ((String) in.readValue((String.class.getClassLoader())));
        this.camchallId = ((String) in.readValue((String.class.getClassLoader())));
        this.camchallStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.participate = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.daysRemaining = ((String) in.readValue((String.class.getClassLoader())));
        this.bannerText = ((String) in.readValue((String.class.getClassLoader())));
        this.nextChallange = ((String) in.readValue((String.class.getClassLoader())));
        this.maintenancePhase = ((MaintenancePhase) in.readValue((MaintenancePhase.class.getClassLoader())));
        this.userGroupId = ((String) in.readValue((String.class.getClassLoader())));
        this.userGroupName = ((String) in.readValue((String.class.getClassLoader())));
        this.dashboardBanner = ((String) in.readValue((String.class.getClassLoader())));
        this.imageUnderDate = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.healthyTips, (com.studio.amplify.pojo.feed_list.HealthyTip.class.getClassLoader()));
        in.readList(this.homeWorkouts, (com.studio.amplify.pojo.feed_list.HomeWorkout.class.getClassLoader()));
        in.readList(this.cardioPlans, (CardioPlan.class.getClassLoader()));
        in.readList(this.partnerOffers, (com.studio.amplify.pojo.feed_list.PartnerOffer.class.getClassLoader()));
        this.isDisabled = ((String) in.readValue((String.class.getClassLoader())));
        this.weekNo = ((String) in.readValue((String.class.getClassLoader())));
        this.unit = ((String) in.readValue((String.class.getClassLoader())));
        this.currentDate = ((String) in.readValue((String.class.getClassLoader())));
        this.healthTipsBanner = ((String) in.readValue((String.class.getClassLoader())));
        this.homeWorkoutsBanner = ((String) in.readValue((String.class.getClassLoader())));
        this.cardioPlansBanner = ((String) in.readValue((String.class.getClassLoader())));
        this.partnerOffersBanner = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public FeedList() {
    }

    /**
     * 
     * @param daysRemaining
     * @param groupId
     * @param currentDate
     * @param partnerOffersBanner
     * @param userGroupId
     * @param healthTipsBanner
     * @param camchallId
     * @param dashboardBanner
     * @param weekNo
     * @param isDisabled
     * @param nextChallange
     * @param maintenancePhase
     * @param partnerOffers
     * @param healthyTips
     * @param homeWorkouts
     * @param sdate
     * @param participate
     * @param camchallStatus
     * @param cardioPlansBanner
     * @param edate
     * @param groupName
     * @param cardioPlans
     * @param unit
     * @param imageUnderDate
     * @param homeWorkoutsBanner
     * @param userGroupName
     * @param name
     * @param bannerText
     */
    public FeedList(Object groupId, Object groupName, String sdate, String edate, String camchallId, String camchallStatus, String name, Integer participate, String daysRemaining, String bannerText, String nextChallange, MaintenancePhase maintenancePhase, String userGroupId, String userGroupName, String dashboardBanner, String imageUnderDate, List<HealthyTip> healthyTips, List<HomeWorkout> homeWorkouts, List<CardioPlan> cardioPlans, List<PartnerOffer> partnerOffers, String isDisabled, String weekNo, String unit, String currentDate, String healthTipsBanner, String homeWorkoutsBanner, String cardioPlansBanner, String partnerOffersBanner) {
        super();
        this.groupId = groupId;
        this.groupName = groupName;
        this.sdate = sdate;
        this.edate = edate;
        this.camchallId = camchallId;
        this.camchallStatus = camchallStatus;
        this.name = name;
        this.participate = participate;
        this.daysRemaining = daysRemaining;
        this.bannerText = bannerText;
        this.nextChallange = nextChallange;
        this.maintenancePhase = maintenancePhase;
        this.userGroupId = userGroupId;
        this.userGroupName = userGroupName;
        this.dashboardBanner = dashboardBanner;
        this.imageUnderDate = imageUnderDate;
        this.healthyTips = healthyTips;
        this.homeWorkouts = homeWorkouts;
        this.cardioPlans = cardioPlans;
        this.partnerOffers = partnerOffers;
        this.isDisabled = isDisabled;
        this.weekNo = weekNo;
        this.unit = unit;
        this.currentDate = currentDate;
        this.healthTipsBanner = healthTipsBanner;
        this.homeWorkoutsBanner = homeWorkoutsBanner;
        this.cardioPlansBanner = cardioPlansBanner;
        this.partnerOffersBanner = partnerOffersBanner;
    }

    public Object getGroupId() {
        return groupId;
    }

    public void setGroupId(Object groupId) {
        this.groupId = groupId;
    }

    public Object getGroupName() {
        return groupName;
    }

    public void setGroupName(Object groupName) {
        this.groupName = groupName;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getCamchallId() {
        return camchallId;
    }

    public void setCamchallId(String camchallId) {
        this.camchallId = camchallId;
    }

    public String getCamchallStatus() {
        return camchallStatus;
    }

    public void setCamchallStatus(String camchallStatus) {
        this.camchallStatus = camchallStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParticipate() {
        return participate;
    }

    public void setParticipate(Integer participate) {
        this.participate = participate;
    }

    public String getDaysRemaining() {
        return daysRemaining;
    }

    public void setDaysRemaining(String daysRemaining) {
        this.daysRemaining = daysRemaining;
    }

    public String getBannerText() {
        return bannerText;
    }

    public void setBannerText(String bannerText) {
        this.bannerText = bannerText;
    }

    public String getNextChallange() {
        return nextChallange;
    }

    public void setNextChallange(String nextChallange) {
        this.nextChallange = nextChallange;
    }

    public MaintenancePhase getMaintenancePhase() {
        return maintenancePhase;
    }

    public void setMaintenancePhase(MaintenancePhase maintenancePhase) {
        this.maintenancePhase = maintenancePhase;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public String getDashboardBanner() {
        return dashboardBanner;
    }

    public void setDashboardBanner(String dashboardBanner) {
        this.dashboardBanner = dashboardBanner;
    }

    public String getImageUnderDate() {
        return imageUnderDate;
    }

    public void setImageUnderDate(String imageUnderDate) {
        this.imageUnderDate = imageUnderDate;
    }

    public List<HealthyTip> getHealthyTips() {
        return healthyTips;
    }

    public void setHealthyTips(List<HealthyTip> healthyTips) {
        this.healthyTips = healthyTips;
    }

    public List<HomeWorkout> getHomeWorkouts() {
        return homeWorkouts;
    }

    public void setHomeWorkouts(List<HomeWorkout> homeWorkouts) {
        this.homeWorkouts = homeWorkouts;
    }

    public List<CardioPlan> getCardioPlans() {
        return cardioPlans;
    }

    public void setCardioPlans(List<CardioPlan> cardioPlans) {
        this.cardioPlans = cardioPlans;
    }

    public List<PartnerOffer> getPartnerOffers() {
        return partnerOffers;
    }

    public void setPartnerOffers(List<PartnerOffer> partnerOffers) {
        this.partnerOffers = partnerOffers;
    }

    public String getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(String isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getWeekNo() {
        return weekNo;
    }

    public void setWeekNo(String weekNo) {
        this.weekNo = weekNo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getHealthTipsBanner() {
        return healthTipsBanner;
    }

    public void setHealthTipsBanner(String healthTipsBanner) {
        this.healthTipsBanner = healthTipsBanner;
    }

    public String getHomeWorkoutsBanner() {
        return homeWorkoutsBanner;
    }

    public void setHomeWorkoutsBanner(String homeWorkoutsBanner) {
        this.homeWorkoutsBanner = homeWorkoutsBanner;
    }

    public String getCardioPlansBanner() {
        return cardioPlansBanner;
    }

    public void setCardioPlansBanner(String cardioPlansBanner) {
        this.cardioPlansBanner = cardioPlansBanner;
    }

    public String getPartnerOffersBanner() {
        return partnerOffersBanner;
    }

    public void setPartnerOffersBanner(String partnerOffersBanner) {
        this.partnerOffersBanner = partnerOffersBanner;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(groupId);
        dest.writeValue(groupName);
        dest.writeValue(sdate);
        dest.writeValue(edate);
        dest.writeValue(camchallId);
        dest.writeValue(camchallStatus);
        dest.writeValue(name);
        dest.writeValue(participate);
        dest.writeValue(daysRemaining);
        dest.writeValue(bannerText);
        dest.writeValue(nextChallange);
        dest.writeValue(maintenancePhase);
        dest.writeValue(userGroupId);
        dest.writeValue(userGroupName);
        dest.writeValue(dashboardBanner);
        dest.writeValue(imageUnderDate);
        dest.writeList(healthyTips);
        dest.writeList(homeWorkouts);
        dest.writeList(cardioPlans);
        dest.writeList(partnerOffers);
        dest.writeValue(isDisabled);
        dest.writeValue(weekNo);
        dest.writeValue(unit);
        dest.writeValue(currentDate);
        dest.writeValue(healthTipsBanner);
        dest.writeValue(homeWorkoutsBanner);
        dest.writeValue(cardioPlansBanner);
        dest.writeValue(partnerOffersBanner);
    }

    public int describeContents() {
        return  0;
    }

}
