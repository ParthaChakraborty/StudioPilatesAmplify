package com.studio.amplify.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;

import com.studio.amplify.R;
import com.studio.amplify.fragment.MealPlanFragment;

import java.text.DecimalFormat;


public class CommonApi {

    private final static String TAG = CommonApi.class.getCanonicalName();
    private static String AILERON_BOLD_WEBFONT = "fonts/AILERON-BOLD-WEBFONT.TTF";
    private static String AILERON_REGULAR_WEBFONT = "fonts/AILERON-REGULAR-WEBFONT.TTF";
    private static String RFG7 = "fonts/RFG79 (6).OTF";
    //private static CommonApi commonApiInstance = null;
    public Activity activity;
    public ProgressDialog progressDialog;
    public String errorMsg = "";

    public CommonApi(Activity activity) {
        this.activity = activity;
        // Exists only to defeat instantiation.
    }

    public CommonApi(MealPlanFragment activity) {
    }

    /*public static CommonApi getInstance(Activity activity) {
        if (commonApiInstance == null) {
            commonApiInstance = new CommonApi();
        }
        commonApiInstance.activity = activity;
        return commonApiInstance;
    }*/

    public static void showToastMessage(Activity activity, String message) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) ((Activity) activity).findViewById(R.id.customToast));
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);
        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

     public void  showDialog(final Activity mActivity, String message, String title, int color, final boolean activityStatus) {
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(mActivity, R.style.myDialog));
            builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (activityStatus) {
                                mActivity.finish();
                            }
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isInternetAvailable(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if network is NOT available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void openNewScreen(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(
                activity.getApplicationContext(), cls);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivity(intent);
        /*activity.overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);*/
        activity.overridePendingTransition(
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }


    // email validation
    public boolean checkEmailValidation(String text) {
        if (!(text.length() > 0)) {
            // showToastMsg("Please enter your email Id");
            return false;
        } else {
            boolean result = Patterns.EMAIL_ADDRESS.matcher(text).matches();
            if (!result) {
                //   showToastMsg("Please enter valid email id");
            }
            return result;
        }
    }

    public String checkRequiredFields(ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup)
                checkRequiredFields((ViewGroup) view);

            else if (view instanceof EditText) {
                EditText edittext = (EditText) view;
                if (edittext.getText().toString().trim().equals("")) {
//                    android.support.design.widget.TextInputLayout parent = (android.support.design.widget.TextInputLayout) edittext.getParent();
                    if (errorMsg.equals(""))
                        errorMsg = "" + edittext.getTag();
                }
            }
        }
        return errorMsg;
    }

    public void finishActivity(Activity activity) {
        activity.finish();
    }

    public void showProgressDialog(String msg) {
        progressDialog = new ProgressDialog(activity, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
        progressDialog.cancel();
        progressDialog.hide();
    }

    public String getAileronBoldWebfront() {
        return AILERON_BOLD_WEBFONT;
    }

    public String getAileronRegularWebfront() {
        return AILERON_REGULAR_WEBFONT;
    }

    public String getRFG7() {
        return RFG7;
    }

    public void closeKeyBoard() {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String toConvertInDecimal(String value) {
        String val;
        Float stringToFloat = Float.parseFloat(value);
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        val = df.format(stringToFloat);
        return val;
    }

    public void enableViews(View v, boolean enabled) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                enableViews(vg.getChildAt(i), enabled);
            }
        }
        v.setEnabled(enabled);
    }
}
