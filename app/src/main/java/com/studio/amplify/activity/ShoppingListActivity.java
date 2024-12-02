package com.studio.amplify.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.studio.amplify.R;
import com.studio.amplify.adapter.MealsListAdapter;
import com.studio.amplify.adapter.ViewPagerAdapter;
import com.studio.amplify.model.MealListItem;
import com.studio.amplify.model.ShoppingListItem;
import com.studio.amplify.util.BaseActivity;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingListActivity extends BaseActivity {
    @BindView(R.id.tvForMaintenanceWeek)
    TextView tvForMaintenanceWeek;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.ivBannerImage)
    ImageView ivBannerImage;
    @BindView(R.id.shoppingListText)
    TextView shoppingListText;
    @BindView(R.id.shoppingListTextBlankValue)
    TextView shoppingListTextBlankValue;
    CommonApi commonApi;

    ArrayList<ShoppingListItem> shoppingListItemArrayList;

    @BindView(R.id.radioButtonShpoppingMainStream)
    RadioButton radioButtonShpoppingMainStream;
    @BindView(R.id.radioButtonShoppingVegetarian)
    RadioButton radioButtonShoppingVegetarian;
    @BindView(R.id.toggleShop)
    RadioGroup toggleShop;

    String genderType;
    int shoppingType = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        commonApi = new CommonApi(ShoppingListActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        genderType = loginCredentials.getUserGender();

        shoppingListItemArrayList = new ArrayList<>();

        callListners();
        checkingDietPlanFromUserData();
        toggleShop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonShoppingVegetarian) {
                    shoppingType = 2;
                    getShoppingList();
                } else if (checkedId == R.id.radioButtonShpoppingMainStream) {
                    shoppingType = 1;
                    getShoppingList();
                }
            }
        });

        if (loginCredentials.getUserGender().equalsIgnoreCase("F")) {
            shoppingListTextBlankValue.setText(R.string.global_female);
        } else if (loginCredentials.getUserGender().equalsIgnoreCase("M")) {
            shoppingListTextBlankValue.setText(R.string.global_male);
        } else if(loginCredentials.getUserGender().equalsIgnoreCase("U")) {
            shoppingListTextBlankValue.setText(R.string.other);
        }
    }

    private void callListners() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvForMaintenanceWeek.setText(shoppingListItemArrayList.get(position).getWeek_title());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void checkingDietPlanFromUserData() {
        try {
            if (loginCredentials.getUserGender().equalsIgnoreCase("") ||
                    loginCredentials.getUserDietPlanCode().equalsIgnoreCase("")) {
                shoppingType = 1;
                getShoppingList();
            }
            if (loginCredentials.getUserGender().equalsIgnoreCase("M") &&
                    loginCredentials.getUserDietPlanCode().equalsIgnoreCase("5") ) {
                radioButtonShpoppingMainStream.setChecked(true);
                shoppingType = 1;
                genderType = "M";
                getShoppingList();

            } else if(loginCredentials.getUserGender().equalsIgnoreCase("M") &&
                    loginCredentials.getUserDietPlanCode().equalsIgnoreCase("7")) {
                radioButtonShoppingVegetarian.setChecked(true);
                shoppingType = 2;
                genderType = "M";
                getShoppingList();

            } else if(loginCredentials.getUserGender().equalsIgnoreCase("F") &&
                    loginCredentials.getUserDietPlanCode().equalsIgnoreCase("12")) {
                radioButtonShpoppingMainStream.setChecked(true);
                shoppingType = 1;
                genderType = "F";
               getShoppingList();

            }else if(loginCredentials.getUserGender().equalsIgnoreCase("F") &&
                    loginCredentials.getUserDietPlanCode().equalsIgnoreCase("13")) {
                radioButtonShoppingVegetarian.setChecked(true);
                shoppingType = 2;
                genderType = "F";
              getShoppingList();

            } else if(loginCredentials.getUserGender().equalsIgnoreCase("U") &&
                    loginCredentials.getUserDietPlanCode().equalsIgnoreCase("14")) {
                radioButtonShpoppingMainStream.setChecked(true);
                shoppingType = 1;
                genderType = "U";
                getShoppingList();
            }
            else if(loginCredentials.getUserGender().equalsIgnoreCase("U") &&
                    loginCredentials.getUserDietPlanCode().equalsIgnoreCase("15")) {
                radioButtonShoppingVegetarian.setChecked(true);
                shoppingType = 2;
                genderType = "U";
                getShoppingList();
            }

            System.out.println("shoppingResponse");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getShoppingList() {
        if (!commonApi.isInternetAvailable(this)) {
            commonApi.showDialog(ShoppingListActivity.this, Constant.NO_INTERNET, "", getResources().getColor(R.color.black), false);
            return;
        }
        shoppingListItemArrayList.clear();
        commonApi.showProgressDialog("");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sub_cat", genderType);
            Log.d("TAG", "Urls:" + Urls.SHOPPING_LIST_NEW + shoppingType);
            new GetServiceCall(Urls.SHOPPING_LIST_NEW + shoppingType, GetServiceCall.TYPE_JSONOBJECT_POST, jsonObject) {
                @Override
                public void response(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        System.out.println("ShoppingPage : " + jsonObject.toString());

                        Glide.with(ShoppingListActivity.this).load(Urls.BASE_URL +
                                jsonObject.optString("banner")).into(ivBannerImage);
                        int isSelectedPosition = 0;
                        JSONArray jsonArray = jsonObject.getJSONArray("lists");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            ShoppingListItem shoppingListItem = new ShoppingListItem();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            shoppingListItem.setWeek_title(jsonObject1.optString("week_title"));
                            shoppingListItem.setContent(jsonObject1.optString("content"));
                            shoppingListItem.setIs_selected(jsonObject1.optString("is_selected"));
                            if (jsonObject1.optString("is_selected").equalsIgnoreCase("1")) {
                                isSelectedPosition = i;
                            }
                            shoppingListItemArrayList.add(shoppingListItem);
                        }
                        viewPager.setAdapter(new ViewPagerAdapter(ShoppingListActivity.this,
                                shoppingListItemArrayList));
                        viewPager.setCurrentItem(isSelectedPosition);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.ivForPreviousWeek, R.id.ivForNextWeek, R.id.imgBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivForPreviousWeek:
                if (viewPager.getCurrentItem() - 1 >= 0)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                break;
            case R.id.ivForNextWeek:
                if (viewPager.getCurrentItem() + 1 < shoppingListItemArrayList.size())
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                break;
            case R.id.imgBack:
                commonApi.finishActivity(ShoppingListActivity.this);
                break;
            default:
        }
    }
}