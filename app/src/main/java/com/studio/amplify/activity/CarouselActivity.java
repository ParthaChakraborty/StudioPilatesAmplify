package com.studio.amplify.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.studio.amplify.R;
import com.studio.amplify.adapter.ImageAdapter;
import com.studio.amplify.adapter.ImageSlideAdapter;
import com.studio.amplify.util.BaseActivity;
import com.studio.amplify.util.CommonApi;

import java.util.ArrayList;
import java.util.List;

public class CarouselActivity extends BaseActivity {
    ViewPager viewImage;
    private LinearLayout ll_dots;
    TextView tv_carou_log;
    private CommonApi commonApi;
    private TextView[]tvDots;
    ImageAdapter imageAdapter;
    SliderView image_slider;
    ImageSlideAdapter imageSlideAdapter;
    private Object lock = new Object();

    private int[] sliderImages = {
            R.drawable.carousel_1, R.drawable.carousel_2, R.drawable.carousel_3,R.drawable.carousel_4,
            R.drawable.carousel_5, R.drawable.carousel_6
    };

    private String [] sliderHeading = {
            "", "MOTIVATION + INSPIRATION", "RECIPES + MEAL PLAN", "TRACKING",
            "COMMUNITY CONNECTION",  "HOME WORKOUTS"
    };

    private String [] sliderBody = {
            "Challenge Starts 7 February", "Health articles, videos and tips to keep you on track",
            "A complete 4-week meal featuring 85+ nutritionist designed recipes",
            "Plan and track your progress throughout the Challenge to stay focussed on your goals",
            "Connect with fellow Challengers and stay up to date with studio news",
            "Pilates mat, circuit and stretch workouts"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        commonApi = new CommonApi(this);
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_carousel);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        viewImage = findViewById(R.id.viewImage);
        ll_dots = findViewById(R.id.ll_dots);
        image_slider = findViewById(R.id.image_slider);

        imageAdapter = new ImageAdapter(this);
        viewImage.setAdapter(imageAdapter);


        imageSlideAdapter = new ImageSlideAdapter(sliderImages, sliderHeading, sliderBody);

        image_slider.setSliderAdapter(imageSlideAdapter);
        image_slider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        image_slider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);

        image_slider.setSliderAnimationDuration(3000);
        image_slider.setScrollTimeInMillis(3000);
        image_slider.startAutoCycle();


        addDotsIndicator(0);

        viewImage.addOnPageChangeListener(viewListener);

        tv_carou_log = findViewById(R.id.tv_carou_log);


        callListeners();
    }


    private void addDotsIndicator(int position) {
            ll_dots.removeAllViews();
            tvDots = new TextView[6];
            for (int i = 0; i < tvDots.length; i++) {
                tvDots[i] = new TextView(this);
                tvDots[i].setText(Html.fromHtml("&#8226;"));
                tvDots[i].setTextSize(35);
                tvDots[i].setTextColor(Color.parseColor("#808080"));
                ll_dots.addView(tvDots[i]);
        }
        if(tvDots.length > 0)
            tvDots[position].setTextColor(getResources().getColor(R.color.white));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private void callListeners() {
        tv_carou_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonApi.openNewScreen(LoginActivity.class, null);
            }
        });
    }

}
