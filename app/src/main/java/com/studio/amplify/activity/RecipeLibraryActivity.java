package com.studio.amplify.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.studio.amplify.R;
import com.studio.amplify.adapter.RecipeLibraryListAdapter;
import com.studio.amplify.model.RecipeLibraryListItem;
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

public class RecipeLibraryActivity extends BaseActivity {

    @BindView(R.id.ivBannerImage)
    ImageView ivBannerImage;
    @BindView(R.id.rvForRecipeList)
    RecyclerView rvForRecipeList;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.mealPlanText)
    TextView mealPlanText;
    @BindView(R.id.rootNestedScroll)
    NestedScrollView rootNestedScroll;

    ArrayList<RecipeLibraryListItem> recipeLibraryListItemArrayList;
    ArrayList<RecipeLibraryListItem> recipeLibraryListItemArrayListTemp;
    RecipeLibraryListAdapter recipeLibraryListAdapter;

    CommonApi commonApi;
    Bundle bundleData;
   // Typeface feedFont, font, fontBold;
    int count = 1;
    @BindView(R.id.tvBreakfast)
    TextView tvBreakfast;
    @BindView(R.id.tvLunch)
    TextView tvLunch;
    @BindView(R.id.tvDinner)
    TextView tvDinner;
    @BindView(R.id.tvSnacks)
    TextView tvSnacks;
    @BindView(R.id.tvPopularCookingList)
    TextView tvPopularCookingList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        commonApi = new CommonApi(RecipeLibraryActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_library);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ButterKnife.bind(this);

        rvForRecipeList.setNestedScrollingEnabled(false);

        getRecipeListMethod();

        recipeLibraryListItemArrayList = new ArrayList<>();
        recipeLibraryListItemArrayListTemp = new ArrayList<>();
        recipeLibraryListAdapter = new RecipeLibraryListAdapter(recipeLibraryListItemArrayListTemp, RecipeLibraryActivity.this);
        rvForRecipeList.setAdapter(recipeLibraryListAdapter);

        bundleData = new Bundle();
        callListeners();

       /* rvForRecipeList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    count++;
                    getRecipeListMethod();
                }
            }
        });*/

     //  if (rootNestedScroll != null) {
           /* rootNestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        count++;
                        getRecipeListMethod();
                    }
                }
            });*/
       // }

    }

    private void callListeners() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    private void filter(String text) {
        recipeLibraryListItemArrayListTemp.clear();
        for (int i = 0; i < recipeLibraryListItemArrayList.size(); i++) {
            if (recipeLibraryListItemArrayList.get(i).getTitle().toLowerCase().contains(text.toLowerCase())) {
                RecipeLibraryListItem searchItem = recipeLibraryListItemArrayList.get(i);
                recipeLibraryListItemArrayListTemp.add(searchItem);
            }
            recipeLibraryListAdapter.notifyDataSetChanged();
        }
    }

    private void getRecipeListMethod() {
        commonApi.showProgressDialog("");
        Log.d("TAG", "Urls:" + Urls.ALL_RECIPE_LIST + count);
        new GetServiceCall(Urls.ALL_RECIPE_LIST + count, GetServiceCall.TYPE_JSONOBJECT) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println("recipeRes:" + response);
                    Glide.with(RecipeLibraryActivity.this).load(Urls.BASE_URL + jsonObject.optString("banner")).into(ivBannerImage);

                    JSONArray jsonArray = jsonObject.getJSONArray("lists");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        RecipeLibraryListItem recipeLibraryListItem = new RecipeLibraryListItem();
                        JSONObject jsonObjectContent = jsonArray.getJSONObject(i);
                        recipeLibraryListItem.setTitle(jsonObjectContent.optString("title"));

                        recipeLibraryListItem.setMeal_timing(jsonObjectContent.optString("meal_timing"));
                        recipeLibraryListItem.setImage(jsonObjectContent.optString("image"));
                        recipeLibraryListItem.setUrl(jsonObjectContent.optString("url"));
                        recipeLibraryListItemArrayList.add(recipeLibraryListItem);
                    }
                    recipeLibraryListItemArrayListTemp.addAll(recipeLibraryListItemArrayList);
                    recipeLibraryListAdapter.notifyDataSetChanged();

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

    @OnClick({R.id.rlBreakfast, R.id.rlLunch, R.id.rlDinner, R.id.rlSnacks, R.id.rlPopularCookingList, R.id.imgBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlBreakfast:
                bundleData.putString("urlKeyForRecipe", "breakfast");
                commonApi.openNewScreen(RecipeLibraryCategoriseActivity.class, bundleData);
                break;
            case R.id.rlLunch:
                bundleData.putString("urlKeyForRecipe", "lunch");
                commonApi.openNewScreen(RecipeLibraryCategoriseActivity.class, bundleData);
                break;
            case R.id.rlDinner:
                bundleData.putString("urlKeyForRecipe", "dinner");
                commonApi.openNewScreen(RecipeLibraryCategoriseActivity.class, bundleData);
                break;
            case R.id.rlSnacks:
                bundleData.putString("urlKeyForRecipe", "snacks");
                commonApi.openNewScreen(RecipeLibraryCategoriseActivity.class, bundleData);
                break;
            case R.id.rlPopularCookingList:
                break;
            case R.id.imgBack:
                commonApi.finishActivity(RecipeLibraryActivity.this);
                break;
            default:
        }
    }
}
