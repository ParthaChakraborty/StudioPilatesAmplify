package com.studio.amplify.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.studio.amplify.R;
import com.studio.amplify.adapter.ShowListingAdapter;
import com.studio.amplify.model.ShowListItem;
import com.studio.amplify.util.BaseActivity;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.LoginCredentials;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowListingActivity extends BaseActivity {
    @BindView(R.id.headerHeading)
    TextView headerHeading;
    @BindView(R.id.headerBack)
    ImageView headerBack;
    private CommonApi commonApi;

    ArrayList<ShowListItem> showListItemArrayList;
    private RecyclerView rv_for_emoji;
    private ShowListingAdapter showListingAdapter;
    String cam_chl_status;
    int cam_chl_id;
  //  String pointPlanText;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        commonApi = new CommonApi(ShowListingActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_listing);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle b13 = getIntent().getExtras();
       // challange_id = b13.getString("challange_id");

        cam_chl_id = b13.getInt("camchall_id");
        cam_chl_status = b13.getString("camchall_status");

     //   pointPlanText = b13.getString("point_plan_text");
      //  System.out.println("challange_id :" + challange_id);
       // System.out.println("point_plan_text :" + pointPlanText);


        rv_for_emoji = (RecyclerView) findViewById(R.id.rv_for_emoji);
        ButterKnife.bind(this);
        headerHeading.setText(R.string.new_text);

        EmojiDetalis();
    }



    private void EmojiDetalis() {
        if (!commonApi.isInternetAvailable(this)) {
            commonApi.showDialog(ShowListingActivity.this, Constant.NO_INTERNET, "", getResources().getColor(R.color.black), false);
            return;
        }

        commonApi.showProgressDialog("Please wait..");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("camchall_id", cam_chl_id);
            jsonObject.put("camchall_status", cam_chl_status);
            Log.d("TAG", "showUrls:" + Urls.EMOJI_DETAILS + loginCredentials.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.EMOJI_DETAILS + loginCredentials.getUserId(), GetServiceCall.TYPE_JSONOBJECT_POST, jsonObject) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println("ShowListPage : " + jsonObject.toString());
                    showListItemArrayList = new ArrayList<>();
                    JSONArray jArray = jsonObject.getJSONArray("emoji_list");
                    for (int i = 0; i < jArray.length(); i++) {
                        ShowListItem showListItem = new ShowListItem();
                        JSONObject jsonObj = jArray.getJSONObject(i);
                        showListItem.setOn_your_mind(jsonObj.optString("on_your_mind"));
                        showListItem.setEmoji_date(jsonObj.optString("emoji_date"));
                        showListItem.setEmoji_name(jsonObj.optString("emoji_name"));
                        showListItemArrayList.add(showListItem);
                    }

                    showListingAdapter = new ShowListingAdapter(showListItemArrayList, ShowListingActivity.this);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    rv_for_emoji.setLayoutManager(layoutManager);
                    rv_for_emoji.setAdapter(showListingAdapter);

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

    @OnClick(R.id.headerBack)
    public void onViewClicked() {
        commonApi.finishActivity(ShowListingActivity.this);
    }
}