package com.studio.amplify.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
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

public class RecipeLibraryCategoriseActivity extends BaseActivity {
    @BindView(R.id.headerHeading)
    TextView headerHeading;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.rvForCategoriesRecipeList)
    RecyclerView rvForCategoriesRecipeList;
    @BindView(R.id.rootNestedScroll)
    NestedScrollView rootNestedScroll;
    ArrayList<RecipeLibraryListItem> recipeLibraryListItemArrayList;
    ArrayList<RecipeLibraryListItem> recipeLibraryListItemArrayListTemp;

    String urlKeys;
    CommonApi commonApi;
    RecipeLibraryListAdapter recipeLibraryListAdapter;
    int count = 1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        commonApi = new CommonApi(RecipeLibraryCategoriseActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_library_category);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.bind(this);
        headerHeading.setText(R.string.recipe_library);

        Bundle bundleReceived = getIntent().getExtras();
        urlKeys = bundleReceived.getString("urlKeyForRecipe");
        recipeLibraryListItemArrayList = new ArrayList<>();
        recipeLibraryListItemArrayListTemp = new ArrayList<>();
        recipeLibraryListAdapter = new RecipeLibraryListAdapter(recipeLibraryListItemArrayListTemp, RecipeLibraryCategoriseActivity.this);
        rvForCategoriesRecipeList.setAdapter(recipeLibraryListAdapter);

        rvForCategoriesRecipeList.setNestedScrollingEnabled(false);
        callListeners();


        /*rvForCategoriesRecipeList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    count++;
                    getDetailForCategoriesRecipeList();
                }
            }
        });
        getDetailForCategoriesRecipeList();

    }
*/
       if (rootNestedScroll != null) {
            rootNestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        count++;
                        getDetailForCategoriesRecipeList();
                    }
                }
            });
        }

        getDetailForCategoriesRecipeList();
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

    private void getDetailForCategoriesRecipeList() {
        recipeLibraryListItemArrayList.clear();
        commonApi.showProgressDialog("");
        Log.d("TAG", "Urlsqw:" + Urls.CATEGORYWISE_RECIPE_LIST + urlKeys + "/paged/" + count);
        new GetServiceCall(Urls.CATEGORYWISE_RECIPE_LIST + urlKeys + "/paged/" + count, GetServiceCall.TYPE_JSONOBJECT) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println("recipeListRes:" + response);
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

            }
        }.call();

    }

    @OnClick({R.id.rlBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlBack:
                commonApi.finishActivity(RecipeLibraryCategoriseActivity.this);
                break;
            default:
        }
    }

    private void filter(String text) {
        recipeLibraryListItemArrayListTemp.clear();
        for (int i = 0; i < recipeLibraryListItemArrayList.size(); i++) {
            if (recipeLibraryListItemArrayList.get(i).getTitle().toLowerCase().contains(text.toLowerCase())) {
                RecipeLibraryListItem serachItem = recipeLibraryListItemArrayList.get(i);
                recipeLibraryListItemArrayListTemp.add(serachItem);
            }
            recipeLibraryListAdapter.notifyDataSetChanged();
        }
    }

}
