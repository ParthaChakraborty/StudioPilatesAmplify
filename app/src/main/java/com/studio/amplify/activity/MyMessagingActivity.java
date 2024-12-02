package com.studio.amplify.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.studio.amplify.R;
import com.studio.amplify.adapter.MyMessagingAdapter;
import com.studio.amplify.model.MyMessagingItem;
import com.studio.amplify.util.BaseActivity;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyMessagingActivity extends BaseActivity {
    @BindView(R.id.headerHeading)
    TextView headerHeading;
    @BindView(R.id.headerBack)
    ImageView headerBack;
    @BindView(R.id.rv_for_notification_listing)
    RecyclerView rv_for_notification_listing;

    ArrayList<MyMessagingItem> messagingItemArrayList;
    private CommonApi commonApi;

    private MyMessagingAdapter myMessagingAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        commonApi = new CommonApi(MyMessagingActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messaging);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        headerHeading.setText(R.string.my_messaging);
        NotificationList();
    }

    private void NotificationList() {
        commonApi.showProgressDialog("");
        String URL = Urls.ADMIN_NOTIFICATION + loginCredentials.getUserId();
        System.out.println("getProfile_url: " + URL);
        StringRequest st = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonElements) {
                try {
                    commonApi.dismissProgressDialog();
                    JSONObject jsonObject = new JSONObject(jsonElements);
                    System.out.println("***NotificationPage : " + jsonObject.toString());
                    messagingItemArrayList = new ArrayList<>();
                    JSONArray jArray = jsonObject.getJSONArray("message_list");
                    for (int i = 0; i < jArray.length(); i++) {
                        MyMessagingItem myMessagingItem = new MyMessagingItem();
                        JSONObject jsonObj = jArray.getJSONObject(i);
                        myMessagingItem.setUser_message_id(jsonObj.optString("user_message_id"));
                        myMessagingItem.setMessage(jsonObj.optString("message"));
                        myMessagingItem.setMessage_date(jsonObj.optString("message_date"));
                        messagingItemArrayList.add(myMessagingItem);
                    }

                    myMessagingAdapter = new MyMessagingAdapter(messagingItemArrayList, MyMessagingActivity.this);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    rv_for_notification_listing.setLayoutManager(layoutManager);
                    rv_for_notification_listing.setAdapter(myMessagingAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    commonApi.dismissProgressDialog();
                    if (error.getClass().equals(NoConnectionError.class)) {

                    }
                } else {
                    commonApi.dismissProgressDialog();

                }
                error.printStackTrace();
            }
        });
        st.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 6, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplicationContext()).add(st);
    }


    @OnClick(R.id.headerBack)
    public void onViewClicked() {
        commonApi.finishActivity(MyMessagingActivity.this);
    }
}
