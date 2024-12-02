package com.studio.amplify.util;

public class Urls {
     // Live //
    public static final String BASE_URL = "http://www.studiopilatesamplify.com/";
    // Live //

    // Demo //
   // public static final String BASE_URL = "http://amplyfyapp.tulieservices.org/";
   // Demo //

    /* USER SECTION */
    public static final String USER_REGISTRATION = BASE_URL + "wp-json/api/registration";
    public static final String CHALLENGE_GOALS = BASE_URL + "wp-json/api/mygoals";
    public static final String CHALLENGE_GOALS_SAVE = BASE_URL + "wp-json/api/mygoalsupdate";
    public static final String USER_LOGIN = BASE_URL + "wp-json/api/login";
    public static final String USER_LOGOUT = BASE_URL + "wp-json/api/logout";
    public static final String FORGOT_PASSWORD = BASE_URL + "wp-json/api/forgot-password";
    public static final String CHECK_CURRENT_PASSWORD = BASE_URL + "wp-json/api/change-password/check-current-password";
    public static final String CHANGE_PASSWORD = BASE_URL + "wp-json/api/change-password";
    /* USER SECTION */

    /*FEED SECTION */
    public static final String GET_DATA_FOR_DASHBOARD = BASE_URL + "wp-json/api/dashboard";
    public static final String GOAL_SETTING = BASE_URL + "wp-json/api/maintenance-phase/";
    /*FEED SECTION */



    /*MEALS SECTION */
    public static final String GET_DATA_FOR_MEALS_LIST = BASE_URL + "wp-json/api/meal/";
    public static final String GET_HEALTH_TIPS_LISTING = BASE_URL + "wp-json/api/health-tips/paged/";
    /*MEALS SECTION */

    /*TRACKING SECTION */
    public static final String GET_TRACKING_PAGE_DETAIL = BASE_URL + "wp-json/api/tracking/";
    public static final String EMOJI_BASED_FEEDBACK = BASE_URL + "wp-json/api/emoji/user/";
    public static final String SAVE_TRACKING_PAGE_DETAIL = BASE_URL + "wp-json/api/tracking-save/user/";
    public static final String FOR_AWARD = BASE_URL + "wp-json/api/tracking/award/user/";
    /*TRACKING SECTION */

    /*LISTING SECTION */
    public static final String EMOJI_DETAILS = BASE_URL + "wp-json/api/emoji-details/user/";
    /*LISTING SECTION */

    /*EDIT PROFILE SECTION */
    public static final String EDIT_PROFILE_UPDATE = BASE_URL + "wp-json/api/profile-update/";
    public static final String UPDATE_DIET_PLAN = BASE_URL + "wp-json/api/profile-update/diet-plan";

    /*EDIT PROFILE SECTION */


    /*RECIPE LIBRARY SECTION */
    public static final String ALL_RECIPE_LIST = BASE_URL + "wp-json/api/recipe-list/all/paged/";
    public static final String CATEGORYWISE_RECIPE_LIST = BASE_URL + "wp-json/api/recipe-list/";
    /*RECIPE LIBRARY SECTION */

    /*CHALLENGE AND CAMPAIGN SECTION */
    public static final String UPCOMING_CHALLENGES = BASE_URL + "wp-json/api/upcomingchallanges/";
    public static final String UPCOMING_CAMPAIGNS = BASE_URL + "wp-json/api/upcomingcampaigns/";
    public static final String ACTIVE_CAMPAIGNS = BASE_URL + "wp-json/api/activecampaigns/";
    public static final String ACTIVE_CHALLENGES = BASE_URL + "wp-json/api/activechallanges/";
    public static final String PARTICIPATE_CAMPAIGNS_CHALLENGES = BASE_URL + "wp-json/api/camchallparticipate";
    public static final String CHALLENGES_LIST = BASE_URL + "wp-json/api/userchallangeslist/";
    public static final String CAMPAIGN_LIST = BASE_URL + "wp-json/api/usercampaignslist/";
    public static final String HISTORY = BASE_URL + "/wp-json/api/history/";
    public static final String GET_GROUP_LIST = BASE_URL + "wp-json/api/groups";
    /*CHALLENGE AND CAMPAIGN SECTION */


    public static final String HOME_WORKOUTS = BASE_URL + "wp-json/api/home-workouts/paged/";
    public static final String CARDIO_PLAN = BASE_URL +"wp-json/api/cardio-plans/paged/";

    public static final String SCHEDULE_CLASS = BASE_URL + "wp-json/api/schedule-classes";
    public static final String BUY_CLASS = BASE_URL + "wp-json/api/buy-classes";
    public static final String GET_COUNTRY_LIST = BASE_URL + "wp-json/api/countries";
    public static final String GET_STUDIO_LIST = BASE_URL + "wp-json/api/studio";
    public static final String GET_DETAIL_FOR_FAQ = BASE_URL + "wp-json/api/faq";
    public static final String CHALLENGE_INTRODUCTION = "wp-json/api/about/54";
    public static final String PRIVACY_POLICY = "wp-json/api/privacy-policy/58";
    public static final String TERMS_AND_CONDITION = "wp-json/api/terms-conditions/56";
    public static final String FORUM = BASE_URL + "wp-json/api/forumurl";
    //public static final String SHOPPING_LIST = BASE_URL + "wp-json/api/shopping-lists";

    public static final String SHOPPING_LIST_NEW = BASE_URL + "wp-json/api/shopping-lists-new/";
    public static final String ADMIN_NOTIFICATION = BASE_URL + "wp-json/api/admin-notification/user/";
    public static final String ADMIN_NOTIFICATION_DETAILS = "wp-json/api/admin-notification-details/usermsgid/";
    public static final String MEAL_PLAN_DETAILS = BASE_URL + "wp-json/api/meals";
    public static final String TITLE_LIST = BASE_URL + "wp-json/api/myclassesgoalslist";
    public static final String UPDATE_GOALS = BASE_URL + "wp-json/api/myclassesgoalsupdate";
    public static final String SHOW_DATES = BASE_URL + "wp-json/api/myclassesgoals";
    public static final String COMMUNITY = "www.studiopilatesamplify.com/?email=";

    /* CLASS SECTION  */
    public static final String GET_CLASS_LIST = BASE_URL +"/wp-json/api/classnumberlists/";
    public static final String GET_TODAYS_CLASS = BASE_URL +"wp-json/api/completed/";


}
