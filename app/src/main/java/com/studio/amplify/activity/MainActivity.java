package com.studio.amplify.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.volley.VolleyError;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.studio.amplify.R;
import com.studio.amplify.fragment.FeedFragment;
import com.studio.amplify.fragment.MealPlanFragment;
import com.studio.amplify.fragment.MoreFragment;
import com.studio.amplify.fragment.TipsFragment;
import com.studio.amplify.fragment.TrackingFragment;
import com.studio.amplify.model.ChallengeCampaignListItem;
import com.studio.amplify.model.CheckboxItem;
import com.studio.amplify.model.UpcomingChallengeAndCampaignsItem;
import com.studio.amplify.util.BaseActivity;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.ForceUpdateChecker;
import com.studio.amplify.util.LoadingInterface;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements LoadingInterface {

    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.headerHeading)
    TextView headerHeading;
    Menu menu;
    String currentVersion, latestVersion;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        headerHeading.setText(R.string.app_name);
        forceUpdate();
        setupBottomNavigationView();

       //FeedFragment feedFragment=new FeedFragment();
        //Bundle b = new Bundle();
       // b.putString("From", "Splash");
        //getDataForDashboardDetails();
        //feedFragment.setArguments(b);
        //pushFragment(feedFragment);

        //forceUpdate();

    }

    private void setupBottomNavigationView() {
        BottomNavigationViewHelper.removeShiftMode(bottomNavigation);
        if (bottomNavigation != null) {
            menu = bottomNavigation.getMenu();
            selectFragment(menu.getItem(0));
            bottomNavigation.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            selectFragment(item);
                            return false;
                        }
                    });

        }
    }//End of setupBottomNavigationView

    protected void pushFragment(Fragment fragment) {
        if (fragment == null)
            return;

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.rootLayout, fragment);
                ft.commit();
            }
        }
    } // End of pushFragment

    protected void selectFragment(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.actionFeed:
                pushFragment(new FeedFragment());
                break;
            case R.id.actionTracking:
                pushFragment(new TrackingFragment());
                break;
            case R.id.actionNutrition:
                pushFragment(new MealPlanFragment());
                break;
            case R.id.actionTips:
                pushFragment(new TipsFragment());
                break;
            case R.id.actionMore:
                pushFragment(new MoreFragment());
                break;
        }

    } // End of selectFragment

    @OnClick(R.id.rlBack)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onRedirect(UpcomingChallengeAndCampaignsItem upcomingChallengeAndCampaignsItem, int userId, String type) {

    }

    @Override
    public void onRedirectFromMyCampaignsAndChallenges(ChallengeCampaignListItem challengeCampaignListItem, int userId, String type) {

    }

    @Override
    public void onRediredectToMainActivity() {
        menu.getItem(0).setChecked(true);

    }

    static class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        public static void removeShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    item.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
                    menuView.buildMenuView();
                    // item.setShiftingMode(false);
                    // item.setChecked(item.getItemData().isChecked());
                }


            } catch (NoSuchFieldException e) {
            } catch (IllegalAccessException e) {
            }
        }
    } //End of BottomNavigationViewHelper

    @Override
    protected void onResume() {
        super.onResume();
        Fragment currentFragment = getFragmentManager().findFragmentById(R.id.rootLayout);
        if (currentFragment instanceof TrackingFragment) {
            Log.v("Tracking Fragment", "your Fragment is Visible");
            ((TrackingFragment) currentFragment).onResume();
        }
    }

    private void forceUpdate() {

        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;

        try {
            pInfo =  pm.getPackageInfo(this.getPackageName(),0);

        } catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        currentVersion = pInfo.versionName;

        new GetLatestVersion().execute();
    }

    private class GetLatestVersion extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                    //It retrieves the latest version by scraping the content of current version from play store at runtime
                Document doc = Jsoup.connect("https://play.google.com/store/apps/details?id="+getPackageName()).get();
                latestVersion = doc.getElementsByClass("htlgb").get(6).text();
            } catch (Exception e){
                e.printStackTrace();
            }

            return new JSONObject();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if(latestVersion!=null) {
                if (!currentVersion.equalsIgnoreCase(latestVersion)){
                    if(!isFinishing()){
                        showUpdateDialog();
                    }
                }
            }
            else
               // background.start();
            super.onPostExecute(jsonObject);
        }
    }

    private void showUpdateDialog(){

        final Dialog appUpdateDialog = new Dialog(this);
        appUpdateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        appUpdateDialog.setContentView(R.layout.app_update_cancel_dialog);
        appUpdateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        appUpdateDialog.setCancelable(false);

        TextView tvTitle = appUpdateDialog.findViewById(R.id.tvTitle);
        TextView tvMessage = appUpdateDialog.findViewById(R.id.tvMessage);
        TextView tvUpdate = appUpdateDialog.findViewById(R.id.tvUpdate);

        tvTitle.setText("Update Available");
        tvMessage.setText(getResources().getString(R.string.new_version_available));

        tvUpdate.setText("Update");
        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }

                appUpdateDialog.dismiss();
            }
        });

        appUpdateDialog.show();
    }
}

