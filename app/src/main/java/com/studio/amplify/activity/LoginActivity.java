package com.studio.amplify.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.studio.amplify.R;
import com.studio.amplify.util.BaseActivity;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.etLoginEmail)
    EditText etLoginEmail;
    @BindView(R.id.etLoginPassword)
    EditText etLoginPassword;
    @BindView(R.id.lnLogin)
    LinearLayout lnLogin;
    @BindView(R.id.tvRegistration)
    TextView tvRegistration;
    @BindView(R.id.tvForgetPassword)
    TextView tvForgetPassword;
    @BindView(R.id.btnLogin)
    TextView btnLogin;
    private CommonApi commonApi;
    //////////////////////fcm/////////////////////
    private FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    private HashMap<String, Object> firebaseDefaultMap;
    public static final String VERSION_CODE_KEY = "latest_app_version";
    private static final String TAG = "LoginActivity";
    //////////////////////fcm/////////////////

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        commonApi = new CommonApi(LoginActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        callListeners();

        //fireBase

            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this, new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    String newToken = instanceIdResult.getToken();
                    Constant.FirebaseDeviceId = newToken;
                    System.out.println("Device_ID: " + Constant.FirebaseDeviceId);
                }
            }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("fireBaseError: " + e.toString());
                }
            });

        //fireBase
    }//onCreate

    private void callListeners() {
        etLoginPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginSpaceCheck();
                    return true;
                }
                return false;
            }
        });
    }


            @OnClick({R.id.btnLogin, R.id.tvRegistration, R.id.tvForgetPassword})
            public void onViewClicked(View view) {
                switch (view.getId()) {
                    case R.id.btnLogin:
                        loginSpaceCheck();
                        break;
                    case R.id.tvRegistration:
                        commonApi.openNewScreen(RegistrationActivity.class, null);
                        commonApi.finishActivity(LoginActivity.this);
                        break;
                    case R.id.tvForgetPassword:
                         commonApi.openNewScreen(ForgotPasswordActivity.class, null);
                        break;
                }
            }


    private void loginSpaceCheck() {
        commonApi.errorMsg = "";
        commonApi.errorMsg = commonApi.checkRequiredFields(lnLogin);
        if (commonApi.errorMsg.equals("")) {
            loginUser();
        } else {
            commonApi.showDialog(LoginActivity.this, commonApi.errorMsg, "", getResources().getColor(R.color.black), false);

        }
    }

    private void loginUser() {
        if (!commonApi.checkEmailValidation(etLoginEmail.getText().toString().trim())) {
            commonApi.showDialog(LoginActivity.this, Constant.EMAIL_VALIDATION, "", getResources().getColor(R.color.black), false);
            return;
        }
        commonApi.closeKeyBoard();
        if (!commonApi.isInternetAvailable(this)) {
            commonApi.showDialog(LoginActivity.this, Constant.NO_INTERNET, "", getResources().getColor(R.color.black), false);
            return;
        }
        commonApi.showProgressDialog("Please wait..");
        JSONObject object = new JSONObject();
        try {
            object.put("email", etLoginEmail.getText().toString());
            object.put("password", etLoginPassword.getText().toString());
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
                        Log.d("***user_id", userId);
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

                        System.out.println(" loginObject : " + jsonObject.toString());

                        loginCredentials.storeUserData(userId, etLoginPassword.getText().toString(), firstName, lastName, currentDate, true, gender, countryName,
                                studioId, studioName, email, memberSince, countryCode, dietPlan, timezone, group, groupName, Integer.parseInt("0"), "",agerange,avatar,username, challenge);
                        commonApi.openNewScreen(MainActivity.class, null);
                        commonApi.finishActivity(LoginActivity.this);

                    } else {
                        commonApi.showDialog(LoginActivity.this, jsonObject.getString("sMessage"), "", getResources().getColor(R.color.black), false);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                commonApi.dismissProgressDialog();

            }

            @Override
            public void error(VolleyError error, String errorMsg) {
                commonApi.dismissProgressDialog();
            }
        }.call();

    }



}
