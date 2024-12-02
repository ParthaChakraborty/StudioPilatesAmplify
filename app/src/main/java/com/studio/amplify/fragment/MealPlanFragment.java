package com.studio.amplify.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.studio.amplify.R;
import com.studio.amplify.activity.RecipeLibraryActivity;
import com.studio.amplify.activity.ShoppingListActivity;
import com.studio.amplify.adapter.MealPlanAdapter;
import com.studio.amplify.model.MealPlanItem;
import com.studio.amplify.util.BaseFragment;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MealPlanFragment extends BaseFragment {
    @BindView(R.id.headerHeadings)
    TextView headerHeadings;
    @BindView(R.id.rootForShoping)
    RelativeLayout rootForShoping;
    @BindView(R.id.rootForRecipe)
    RelativeLayout rootForRecipe;
   /* @BindView(R.id.headerBack)
    ImageView headerBack;
    @BindView(R.id.rlBack)
    RelativeLayout rlBack;
    @BindView(R.id.headerRootLayout)
    RelativeLayout headerRootLayout;*/
    @BindView(R.id.imageRecipe)
    ImageView imageRecipe;
    @BindView(R.id.imageShopping)
    ImageView imageShopping;
    Unbinder unbinder;
    @BindView(R.id.rv_for_meal_listing)
    RecyclerView rv_for_meal_listing;
    private CommonApi commonApi;
    private MealPlanAdapter mealPlanAdapter;
    private ArrayList<MealPlanItem> mealsPlanList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.meal_plan_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        unbinder = ButterKnife.bind(this, layout);
        commonApi = new CommonApi(getActivity());
        headerHeadings.setText(R.string.nutrition);
        MealPlan();

        rootForShoping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonApi.openNewScreen(ShoppingListActivity.class, null);
            }
        });

        rootForRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonApi.openNewScreen(RecipeLibraryActivity.class, null);
            }
        });

        return layout;
    }

    private void MealPlan() {
        commonApi.showProgressDialog("Please wait...");
        String URL = Urls.MEAL_PLAN_DETAILS;
        System.out.println("mealResp: " + URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                commonApi.dismissProgressDialog();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    System.out.println("mealsRes:" + response);
                    mealsPlanList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        MealPlanItem mealPlanItem = new MealPlanItem();
                        JSONObject object = jsonArray.getJSONObject(i);

                        mealPlanItem.setImage(object.getString("Image"));
                        mealPlanItem.setHeading(object.getString("Heading"));
                        mealPlanItem.setLink(object.getString("Link"));
                        mealPlanItem.setLinkType(object.getString("Linktype"));
                      //  Glide.with(getActivity()).load(Urls.BASE_URL + object.getString("Image")).into(imageShopping);
                     //   Glide.with(getActivity()).load(Urls.BASE_URL + object.getString("Image")).into(imageRecipe);
                        mealsPlanList.add(mealPlanItem);

                    }
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    rv_for_meal_listing.setLayoutManager(layoutManager);
                    mealPlanAdapter = new MealPlanAdapter(getActivity(), mealsPlanList);
                    rv_for_meal_listing.setAdapter(mealPlanAdapter);


                } catch (JSONException e) {
                    ////
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
