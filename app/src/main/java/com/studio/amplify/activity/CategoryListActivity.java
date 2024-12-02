package com.studio.amplify.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.studio.amplify.R;
import com.studio.amplify.adapter.FeedListAdapter;
import com.studio.amplify.adapter.MealsListAdapter;
import com.studio.amplify.pojo.feed_list.FeedList;
import com.studio.amplify.util.CommonApi;

public class CategoryListActivity extends AppCompatActivity {
    RecyclerView rvForAllList;
    RelativeLayout rlBack;
    private MealsListAdapter mealsListAdapter;
    FeedListAdapter feedListAdapter;
    int flag;
     CommonApi commonApi;
    FeedList feedList;
     TextView headerHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        commonApi = new CommonApi(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        rvForAllList = findViewById(R.id.rvForAllList);
        headerHeading = findViewById(R.id.headerHeading);
        rlBack = findViewById(R.id.rlBack);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");

        try {
            if (args != null) {
                flag = Integer.parseInt(intent.getStringExtra("FLAG_NO"));
                Gson gson = new Gson();
                feedList = gson.fromJson(args.getString("Response") , FeedList.class);

                //all plan model are same so taking Cardio Plans Model in arraylist//
               // plans = args.getParcelableArrayList("ARRAYLIST");
                //plans = (ArrayList<CardioPlans>) args.getSerializable("ARRAYLIST");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(flag == 1) {
            headerHeading.setText("Cardio Plans");
        } else if(flag == 2) {
            headerHeading.setText("Health and Wellness Blogs");
        } else if(flag == 3) {
            headerHeading.setText("Home Workouts");
        } else if(flag == 4) {
            headerHeading.setText("Partner Offers");
        }

        //Toast.makeText(this,cardioPlans.get(0).getUrl(),Toast.LENGTH_LONG).show();
        feedListAdapter = new FeedListAdapter(feedList, this, flag);
        rvForAllList.setAdapter(feedListAdapter);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonApi.finishActivity(CategoryListActivity.this);
            }
        });
    }

   }
