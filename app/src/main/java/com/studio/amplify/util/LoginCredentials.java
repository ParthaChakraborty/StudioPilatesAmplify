package com.studio.amplify.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.studio.amplify.R;

public class LoginCredentials {

    private static LoginCredentials loginCredentialInstance = null;
    private Activity activity;

    private LoginCredentials() {
    }

    public static LoginCredentials getInstance(Activity activity) {
        if (loginCredentialInstance == null) {
            loginCredentialInstance = new LoginCredentials();
        }
        loginCredentialInstance.activity = activity;
        return loginCredentialInstance;
    }

    public void storeUserData(String userId, String userPassword, String userFirstName,
                              String userLastName, String currentDate, boolean isLoggedIn,
                              String userGender, String userCountryName, String userStudioId,
                              String userStudioName, String userEmail, String memberSince,
                              String userCountryCode, String userDietPlan, String useTimeZone,
                              String useGroup, String userGroupName, int chamChlId,
                              String chamChlType,String agerange,int avatar,String username, String challenge) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(activity.getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();

        prefEditor.putString(Constant.USER_ID, userId);
        prefEditor.putString(Constant.AGE_RANGE, agerange);
        prefEditor.putInt(Constant.AVATAR, avatar);
        prefEditor.putString(Constant.USERNAME, username);
        prefEditor.putString(Constant.USER_PASSWORD, userPassword);
        prefEditor.putString(Constant.USER_FIRST_NAME, userFirstName);
        prefEditor.putString(Constant.USER_LAST_NAME, userLastName);
        prefEditor.putString(Constant.CURRENT_DATE, currentDate);
        prefEditor.putBoolean(Constant.LOGGED_IN, isLoggedIn);
        prefEditor.putString(Constant.USER_GENDER, userGender);
        prefEditor.putString(Constant.USER_COUNTRY_NAME, userCountryName);
        prefEditor.putString(Constant.USER_STUDIO_ID, userStudioId);
        prefEditor.putString(Constant.USER_STUDIO_NAME, userStudioName);
        prefEditor.putString(Constant.USER_EMAIL, userEmail);
        prefEditor.putString(Constant.USER_MEMBER_SINCE, memberSince);
        prefEditor.putString(Constant.USER_COUNTRY_CODE, userCountryCode);
        prefEditor.putString(Constant.USER_DIET_PLAN, userDietPlan);
        prefEditor.putString(Constant.USER_TIME_ZONE, useTimeZone);
        prefEditor.putString(Constant.USER_GROUP, useGroup);
        prefEditor.putString(Constant.USER_GROUP_NAME, userGroupName);
        prefEditor.putInt(Constant.CAMP_CHALL_ID, chamChlId);
        prefEditor.putString(Constant.CAMP_CHALL_TYPE, chamChlType);
        prefEditor.putString(Constant.CHALLENGE, challenge);
      //  prefEditor.putString(Constant.USER_UNIT, userUnit);
        prefEditor.apply();
    }

    public String getUserId() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_ID, "");
    }

    public void setUserId(String userId) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_ID, userId);
        prefEditor.apply();
    }

    public String getUserPassword() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_PASSWORD, "");
    }

    public void setUserPassword(String userPassword) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_PASSWORD, userPassword);
        prefEditor.apply();
    }
    public String getUserName() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USERNAME, "");
    }

    public void setUserName(String username) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USERNAME, username);
        prefEditor.apply();
    }
    public int getAvatar() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Constant.AVATAR, 0);
    }

    public void setAvatar(int avatar) {

        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();

            prefEditor.putInt(Constant.AVATAR, avatar);
        prefEditor.apply();
    }
    public String getAgeRange() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.AGE_RANGE, "");
    }

    public void setAgeRange(String ageRange) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.AGE_RANGE, ageRange);
        prefEditor.apply();
    }

    public String getChallenge() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.CHALLENGE, "");
    }

    public void setChallenge(String challenge) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.CHALLENGE, challenge);
        prefEditor.apply();
    }

    public String getUserFirstName() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_FIRST_NAME, "");
    }

    public void setUserFirstName(String userFirstName) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_FIRST_NAME, userFirstName);
        prefEditor.apply();
    }

    public String getUserDietPlan() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_DIET_PLAN, "");
    }

    public void setUserDietPlan(String userDietplan) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_DIET_PLAN, userDietplan);
        prefEditor.apply();
    }

    public String getUserDietPlanCode() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_DIET_PLAN_CODE, "");
    }

    public void setUserDietPlanCode(String userDietplanCode) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_DIET_PLAN_CODE, userDietplanCode);
        prefEditor.apply();
    }

    public String getUserLastName() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_LAST_NAME, "");
    }

    public void setUserLastName(String userLastName) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_LAST_NAME, userLastName);
        prefEditor.apply();
    }

    public String getUserGender() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_GENDER, "");
    }

    public void setUserGender(String userGender) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_GENDER, userGender);
        prefEditor.apply();
    }

    public String getUserStudioId() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_STUDIO_ID, "");
    }

    public void setUserStudioId(String userStudioId) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_STUDIO_ID, userStudioId);
        prefEditor.apply();
    }

    public String getUserStudioName() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_STUDIO_NAME, "");
    }

    public void setUserStudioName(String userStudioName) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_STUDIO_NAME, userStudioName);
        prefEditor.apply();
    }

    public String getUserCountryName() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_COUNTRY_NAME, "");
    }

    public void setUserCountryName(String userCountryName) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_COUNTRY_NAME, userCountryName);
        prefEditor.apply();
    }

    public String getUserCountryCode() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_COUNTRY_CODE, "");
    }

    public void setUserCountryCode(String userCountryCode) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_COUNTRY_CODE, userCountryCode);
        prefEditor.apply();
    }

    public String getUserTimeZone() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_TIME_ZONE, "");
    }

    public void setUserTimeZone(String userTimeZone) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_TIME_ZONE, userTimeZone);
        prefEditor.apply();
    }

    public void setIsLogin(Boolean IS_LoggedIn) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putBoolean(Constant.LOGGED_IN, IS_LoggedIn);
        prefEditor.apply();

    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Constant.LOGGED_IN, false);
    }

    public String getCurrentDate() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.CURRENT_DATE, "");
    }

    public void setCurrentDate(String currentDate) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.CURRENT_DATE, currentDate);
        prefEditor.apply();
    }

    public String getCurrentDateFromFeed() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.CURRENT_DATE_FROM_FEED, "");
    }

    public void setCurrentDateFromFeed(String currentDate) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.CURRENT_DATE_FROM_FEED, currentDate);
        prefEditor.apply();
    }

    public String getUserUnit() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_UNIT, "");
    }

    public void setUserUnit(String userUnit) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_UNIT, userUnit);
        prefEditor.apply();
    }


    public void clearUserData(Context context) {
        SharedPreferences preferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getUserEmail() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_EMAIL, "");
    }

    public void setUserEmail(String userEmail) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_EMAIL, userEmail);
        prefEditor.apply();
    }

    public String getMemberSince() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_MEMBER_SINCE, "");
    }

    public void setMemberSince(String memberSince) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_MEMBER_SINCE, memberSince);
        prefEditor.apply();
    }

    public int getTotalCalculation() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Constant.TOTAL_CALCULATION, 0);
    }

    public void setTotalCalculation(int totalCalculation) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putInt(Constant.TOTAL_CALCULATION, totalCalculation);
        prefEditor.apply();
    }

    public void setChallengeStartDate(String challengeStartDate) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.CHALLENGE_START_DT, challengeStartDate);
        prefEditor.apply();
    }
    public String getChallengeStartDate() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.CHALLENGE_START_DT, "");
    }
    public void setChallengeRemainingDays(String challengeRemainingDays) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        String[] str = challengeRemainingDays.split(" ");  //now str[0] is "hello" and str[1] is "goodmorning,2,1"
        challengeRemainingDays = str[0];
        prefEditor.putString(Constant.CHALLENGE_REM_DAYS, challengeRemainingDays);
        prefEditor.apply();
    }

    public String getChallengeRemainingDays() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.CHALLENGE_REM_DAYS, "");
    }

    public void setCAMP_CHAL_DETAILS(int chamChlId, String type) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putInt(Constant.CAMP_CHALL_ID, chamChlId);
        prefEditor.putString(Constant.CAMP_CHALL_TYPE, type);
        prefEditor.apply();
    }

    public int getCAMP_CHAL_ID() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Constant.CAMP_CHALL_ID, 0);
    }

    public String getCAMP_CHAL_TYPE() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.CAMP_CHALL_TYPE, "");
    }


    public String getUserGroup() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_GROUP, "");
    }

    public void setUserGroup(String userGroup) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_GROUP, userGroup);
        prefEditor.apply();
    }

    public String getUserGroupName() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constant.USER_GROUP_NAME, "");
    }

    public void setUserGroupName(String userGroupName) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(Constant.USER_GROUP_NAME, userGroupName);
        prefEditor.apply();
    }


}
