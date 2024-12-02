package com.studio.amplify.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.studio.amplify.R;
import com.studio.amplify.util.BaseActivity;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreenActivity extends BaseActivity {
    private CommonApi commonApi;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        commonApi = new CommonApi(SplashScreenActivity.this);
        super.onCreate(savedInstanceState);
        //loginCredentials.setCAMP_CHAL_DETAILS(0,"");
       // startService(new Intent(getBaseContext(), OnClearFromRecentService.class));

       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (loginCredentials.getUserId().equals("")) {
            new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    commonApi.openNewScreen(CarouselActivity.class, null);
                    SplashScreenActivity.this.finish();
                }
            }.start();
        } else {

           FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashScreenActivity.this, new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    String newToken = instanceIdResult.getToken();
                    Constant.FirebaseDeviceId = newToken;
                    System.out.println("Device_ID: " + Constant.FirebaseDeviceId);
                    callLoginService();
                }
            }).addOnFailureListener(SplashScreenActivity.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("fireBaseError: " + e.toString());
                }
            });
        }
    }

    private void callLoginService() {
        JSONObject object = new JSONObject();
        try {
            object.put("email", loginCredentials.getUserEmail());
            object.put("password", loginCredentials.getUserPassword());
            object.put("device_id", Constant.FirebaseDeviceId);
            object.put("device_type", "android");
            Log.d("TAG", "JSonObject" + object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.USER_LOGIN, GetServiceCall.TYPE_JSONOBJECT_POST, object) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("sStatus").equalsIgnoreCase("1")) {
                        String userId = jsonObject.optString("user_id");
                        String firstName = jsonObject.optString("first_name");
                        String lastName = jsonObject.optString("last_name");
                        String currentDate = jsonObject.optString("current_date");
                        String gender = jsonObject.optString("gender");
                        String countryName = jsonObject.optString("country_name");
                        String countryCode = jsonObject.optString("country_code");
                        String studioId = jsonObject.optString("studio");
                        String studioName = jsonObject.optString("studio_name");
                        String email = jsonObject.optString("email");
                        String memberSince = jsonObject.optString("member_since");
                        String dietPlan = jsonObject.optString("diet_plan");
                        String timezone = jsonObject.optString("timezone_name");
                        String group = jsonObject.optString("group");
                        String groupName = jsonObject.optString("group_name");
                        String agerange = jsonObject.optString("agerange");
                        int avatar = jsonObject.optInt("avatar");
                        String username = jsonObject.optString("username");
                        String challenge = jsonObject.optString("challange");

                        loginCredentials.storeUserData(userId, loginCredentials.getUserPassword(), firstName,
                                lastName, currentDate, true, gender, countryName, studioId, studioName,
                                email, memberSince, countryCode, dietPlan, timezone, group, groupName, Integer.parseInt("0"), "",agerange,avatar,username, challenge);
                        commonApi.openNewScreen(MainActivity.class, null);
                        commonApi.finishActivity(SplashScreenActivity.this);

                    } else {
                        loginCredentials.clearUserData(SplashScreenActivity.this);
                        commonApi.openNewScreen(LoginActivity.class, null);
                        commonApi.finishActivity(SplashScreenActivity.this);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    loginCredentials.clearUserData(SplashScreenActivity.this);
                    commonApi.openNewScreen(LoginActivity.class, null);
                    commonApi.finishActivity(SplashScreenActivity.this);
                }

            }

            @Override
            public void error(VolleyError error, String errorMsg) {
                loginCredentials.clearUserData(SplashScreenActivity.this);
                commonApi.openNewScreen(LoginActivity.class, null);
                commonApi.finishActivity(SplashScreenActivity.this);
            }
        }.call();
    }
}
