package com.studio.amplify.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.studio.amplify.R;
import com.studio.amplify.util.BaseActivity;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity {

    @BindView(R.id.etForgotEmail)
    EditText etForgotEmail;
    @BindView(R.id.tvForgetYourPassword)
    TextView tvForgetYourPassword;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;
    @BindView(R.id.headerHeading)
    TextView headerHeading;

    private CommonApi commonApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        commonApi = new CommonApi(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        headerHeading.setText(R.string.forget_password);

        callListeners();

    }

    private void callListeners() {
        etForgotEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    commonApi.closeKeyBoard();
                    validateData();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.btnSubmit, R.id.headerBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                commonApi.closeKeyBoard();
                validateData();
                break;
            case R.id.headerBack:
                commonApi.finishActivity(ForgotPasswordActivity.this);
                break;
            default:
        }

    }

    private void validateData() {
        String emailStr = etForgotEmail.getText().toString();
        if (commonApi.checkEmailValidation(emailStr)) {
            ForgotPasswordData();
        } else {
            commonApi.showDialog(this, Constant.EMAIL_VALIDATION, "", getResources().getColor(R.color.black), false);
        }
    }

    private void ForgotPasswordData() {
        if (!commonApi.isInternetAvailable(this)) {
            commonApi.showDialog(ForgotPasswordActivity.this, Constant.NO_INTERNET, "", getResources().getColor(R.color.black), false);
            return;
        }
        commonApi.showProgressDialog("Please wait..");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", etForgotEmail.getText().toString());
            System.out.println(" Object : " + jsonObject.toString());
            System.out.println(" Url : " + Urls.FORGOT_PASSWORD);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.FORGOT_PASSWORD, GetServiceCall.TYPE_JSONOBJECT_POST, jsonObject) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("sStatus").equalsIgnoreCase("1")) {
                        commonApi.showDialog(ForgotPasswordActivity.this, jsonObject.getString("sMessage"), "", getResources().getColor(R.color.black), true);
                    } else {
                        commonApi.showDialog(ForgotPasswordActivity.this, jsonObject.getString("sMessage"), "", getResources().getColor(R.color.black), false);
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
    }// forgetPasswordData
}
