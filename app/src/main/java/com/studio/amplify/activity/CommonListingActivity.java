package com.studio.amplify.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.studio.amplify.R;
import com.studio.amplify.adapter.CommonListAdapter;
import com.studio.amplify.model.CommonListItem;
import com.studio.amplify.util.BaseActivity;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommonListingActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.headerHeading)
    TextView headerHeading;
    @BindView(R.id.etSearch)
    EditText etSearch;
    CommonListAdapter commonListAdapter;
    CommonApi commonApi;
    ArrayList<CommonListItem> commonListItemArrayList;
    ArrayList<CommonListItem> commonListItemArrayListTemp;
    String urlKeyToSend, titleToSet, flagForWebService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        commonApi = new CommonApi(this);
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_common_listing);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        Bundle b1 = getIntent().getExtras();
        if (b1 != null) {
            urlKeyToSend = b1.getString("urlKey");
            titleToSet = b1.getString("title");
        }

        headerHeading.setText(titleToSet);


        if (urlKeyToSend.equalsIgnoreCase("countries")) {
            flagForWebService = "1";
        } else if (urlKeyToSend.equalsIgnoreCase("studio")) {
            flagForWebService = "2";
        } else if(urlKeyToSend.equalsIgnoreCase("timezones")){
            flagForWebService = "3";
        } else if(urlKeyToSend.equalsIgnoreCase("challange")) {
            flagForWebService = "4";
        } /*else if(urlKeyToSend.equalsIgnoreCase("groups")){
            flagForWebService = "4";
        }*/


        commonListItemArrayList = new ArrayList<>();
        commonListItemArrayListTemp = new ArrayList<>();
        commonListAdapter = new CommonListAdapter(commonListItemArrayListTemp, CommonListingActivity.this, flagForWebService);
        recyclerView.setAdapter(commonListAdapter);

        clickListener();
        showCountryListMethod();
    }

    private void clickListener() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println("charSequence: " + charSequence);
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void filter(String text) {
        commonListItemArrayListTemp.clear();
        for (int i = 0; i < commonListItemArrayList.size(); i++) {
            if (commonListItemArrayList.get(i).getName().toLowerCase().contains(text.toLowerCase())) {
                CommonListItem searchedCountry = commonListItemArrayList.get(i);
                commonListItemArrayListTemp.add(searchedCountry);
            }
            commonListAdapter.notifyDataSetChanged();
        }
    }

    private void showCountryListMethod() {
        if (!commonApi.isInternetAvailable(this)) {
            Toast.makeText(this, Constant.NO_INTERNET, Toast.LENGTH_SHORT).show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.BASE_URL + "wp-json/api/" + urlKeyToSend, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    System.out.println(" commonObject : " + response);
                    System.out.println(" commonUrl : " + Urls.BASE_URL + "wp-json/api/" + urlKeyToSend);
                    Log.d("commonResp:", response);
                    commonListItemArrayList.clear();
                    commonListItemArrayListTemp.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        CommonListItem commonListItem = new CommonListItem();
                        JSONObject object = jsonArray.getJSONObject(i);

                        if (urlKeyToSend.equalsIgnoreCase("countries")) {
                            commonListItem.setName(object.getString("country_name"));
                            commonListItem.setId(object.getString("country_code"));
                        } else if (urlKeyToSend.equalsIgnoreCase("studio")) {
                            commonListItem.setName(object.getString("studio_name"));
                            commonListItem.setId(object.getString("studio_id"));
                        } else if(urlKeyToSend.equalsIgnoreCase("timezones")){
                            commonListItem.setId(object.getString("id"));
                            commonListItem.setName(object.getString("name"));
                        } else if(urlKeyToSend.equalsIgnoreCase("challange")){
                            commonListItem.setId(object.getString("id"));
                            commonListItem.setName(object.getString("name"));

                        }
                        /*else if(urlKeyToSend.equalsIgnoreCase("groups")){
                            commonListItem.setId(object.getString("group_id"));
                            commonListItem.setName(object.getString("group_name"));
                        }*/


                        commonListItemArrayList.add(commonListItem);
                    }

                    commonListItemArrayListTemp.addAll(commonListItemArrayList);
                    commonListAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    @OnClick(R.id.rlBack)
    public void onViewClicked() {
        commonApi.finishActivity(CommonListingActivity.this);
    }


}









