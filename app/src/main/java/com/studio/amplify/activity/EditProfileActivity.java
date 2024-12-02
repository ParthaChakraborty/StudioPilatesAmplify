package com.studio.amplify.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.studio.amplify.R;
import com.studio.amplify.adapter.CustomGalleryAdapter;
import com.studio.amplify.util.BaseActivity;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProfileActivity extends BaseActivity {
    //header
    @BindView(R.id.headerHeading)
    TextView headerHeading;
    @BindView(R.id.headerBack)
    ImageView headerBack;
    //header
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageGallery)
    Gallery imageGallery;
    //label
    @BindView(R.id.tvChangePassword)
    TextView tvChangePassword;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvMember)
    TextView tvMember;
    @BindView(R.id.tvGender)
    TextView tvGender;
    @BindView(R.id.tvCountry)
    TextView tvCountry;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvStudio)
    TextView tvStudio;
    @BindView(R.id.tvDiet)
    TextView tvDiet;
    @BindView(R.id.tvUnit)
    TextView tvUnit;
    @BindView(R.id.tvGroup)
    TextView tvGroup;
    /*@BindView(R.id.tvChallenge)
    TextView tvChallenge;*/
    @BindView(R.id.tvAgeRange)
    TextView tvAgeRange;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    //label

    //label Value
    @BindView(R.id.tvNameValue)
    TextView tvNameValue;
    @BindView(R.id.tvEmailValue)
    TextView tvEmailValue;
    @BindView(R.id.tvMemberValue)
    TextView tvMemberValue;
    @BindView(R.id.tvGenderValue)
    TextView tvGenderValue;
    @BindView(R.id.tvCountryValue)
    TextView tvCountryValue;
    @BindView(R.id.tvTimeValue)
    TextView tvTimeValue;
    @BindView(R.id.tvChangePasswordValue)
    TextView tvChangePasswordValue;
    @BindView(R.id.tvDietValue)
    TextView tvDietValue;
    @BindView(R.id.tvStudioValue)
    TextView tvStudioValue;
    @BindView(R.id.tvUnitValue)
    TextView tvUnitValue;
    @BindView(R.id.tvGroupValue)
    TextView tvGroupValue;
    @BindView(R.id.tvAgeRangeVal)
    TextView tvAgeRangeVal;
    @BindView(R.id.tvUserNameValue)
    TextView tvUserNameValue;
    @BindView(R.id.tvChalValue)
    TextView tvChalValue;
    //label Value

    @BindView(R.id.change_bt)
    Button change_bt;
    boolean isChange=true;


    private CommonApi commonApi;
    int avatarPos=0;
    CustomGalleryAdapter customGalleryAdapter;
    int[] images = {R.drawable.avtar_0,R.drawable.avtar_1, R.drawable.avtar_2, R.drawable.avtar_3, R.drawable.avtar_4, R.drawable.avtar_5,
            R.drawable.avtar_6,R.drawable.avtar_7,R.drawable.avtar_8,R.drawable.avtar_9,R.drawable.avtar_10};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        commonApi = new CommonApi(EditProfileActivity.this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        selectAvatar();

        headerHeading.setText(R.string.edit_profile);

        tvNameValue.setText(loginCredentials.getUserFirstName() + " " + loginCredentials.getUserLastName());
        tvEmailValue.setText(loginCredentials.getUserEmail());
        tvMemberValue.setText(loginCredentials.getMemberSince());

        if (loginCredentials.getUserGender().equals("F")) {
            tvGenderValue.setText("Identify as Female");
        } else if(loginCredentials.getUserGender().equals("M")){
            tvGenderValue.setText("Identify as Male");
        } else if(loginCredentials.getUserGender().equals("U")) {
            tvGenderValue.setText("Unspecified/Non-binary");
        }else if(loginCredentials.getUserGender().equals("O")) {
            tvGenderValue.setText("Other");
        } else if(loginCredentials.getUserGender().equals("P")) {
            tvGenderValue.setText("Prefer not to say");
        }

        tvCountryValue.setText(loginCredentials.getUserCountryCode());
        tvStudioValue.setText(loginCredentials.getUserStudioName());
        tvGroupValue.setText(loginCredentials.getUserGroupName());
        tvUserNameValue.setText(loginCredentials.getUserName());

        if (loginCredentials.getUserUnit().equals("kg")) {
            tvUnitValue.setText("kg");
        } else {
            tvUnitValue.setText("lb");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setData();
        setCountry();
        setTimeZone();
        setGroup();

    }


    private void setTimeZone() {
        tvTimeValue.setText(loginCredentials.getUserTimeZone());
    }



    private void setData() {
        tvStudioValue.setText(loginCredentials.getUserStudioName());
        tvEmailValue.setText(loginCredentials.getUserEmail());
        tvMemberValue.setText(loginCredentials.getMemberSince());
        tvNameValue.setText(loginCredentials.getUserFirstName() + " " + loginCredentials.getUserLastName());

        if (loginCredentials.getUserDietPlan().equalsIgnoreCase("")) {
            if (loginCredentials.getUserGender().equals("F")) {
                loginCredentials.setUserDietPlan("Mainstream");
                loginCredentials.setUserDietPlanCode("12");
            } else {
                loginCredentials.setUserDietPlan("Mainstream");
                loginCredentials.setUserDietPlanCode("5");
            }

        }


            tvAgeRangeVal.setText(loginCredentials.getAgeRange());
            tvChalValue.setText(loginCredentials.getChallenge());

        tvDietValue.setText(loginCredentials.getUserDietPlan());

        if (loginCredentials.getUserGender().equals("F")) {
            tvGenderValue.setText("Identify as Female");
            setDietForFemale();
        } else if(loginCredentials.getUserGender().equals("M")){
            tvGenderValue.setText("Identify as Male");
            setDietForMale();
        } else if(loginCredentials.getUserGender().equals("U")) {
            tvGenderValue.setText("Unspecified/Non-binary");
            setDietForUnspecified();
        } else if(loginCredentials.getUserGender().equals("O")) {
            tvGenderValue.setText("Other");
            setDietForOther();
        } else if(loginCredentials.getUserGender().equals("P")) {
            tvGenderValue.setText("Prefer not to say");
            setDietForPrefer();
        }

        if (loginCredentials.getUserUnit().equals("kg")) {
            tvUnitValue.setText("kg");
        } else {
            tvUnitValue.setText("lb");
        }

    }

    private void setDietForMale() {
        if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("7")) {
            tvDietValue.setText("Vegetarian");
        } else {
            tvDietValue.setText("Mainstream");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setDietForFemale() {
        if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("13")) {
            tvDietValue.setText("Vegetarian");
        } else {
            tvDietValue.setText("Mainstream");
        }
    }

    private void setDietForOther() {
        if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("19")) {
            tvDietValue.setText("Vegetarian");
        } else {
            tvDietValue.setText("Mainstream");
        }
    }

    private void setDietForPrefer() {
        if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("22")) {
            tvDietValue.setText("Vegetarian");
        } else {
            tvDietValue.setText("Mainstream");
        }
    }

    private void setDietForUnspecified() {
        if (loginCredentials.getUserDietPlanCode().equalsIgnoreCase("15")) {
            tvDietValue.setText("Vegetarian");
        } else {
            tvDietValue.setText("Mainstream");
        }
    }

    private void setCountry() {
        tvCountryValue.setText(loginCredentials.getUserCountryCode());
    }

    private void setGroup() {
        tvGroupValue.setText(loginCredentials.getUserGroupName());
    }

    @OnClick({R.id.rlForPasswordChange, R.id.headerBack, R.id.rlForUserDiet, R.id.rlForUserGender,
            R.id.rlForUserCountry, R.id.rlForUserStudio, R.id.rlForUserTimeZone, R.id.rlForUnit,
            R.id.rlForAgeRange, R.id.rlForUserChallenge /*, R.id.rlForUserGroup*/,R.id.change_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlForPasswordChange:
                commonApi.openNewScreen(ChangePasswordActivity.class, null);
                break;
            case R.id.headerBack:
                commonApi.finishActivity(EditProfileActivity.this);
                break;
            case R.id.rlForUserDiet:
                Bundle bundles = new Bundle();
                bundles.putInt("fromIntent", 2);
                commonApi.openNewScreen(UpdateGenderAndDietPlanActivity.class, bundles);
                break;
            case R.id.rlForUserGender:
                Bundle bundle = new Bundle();
                bundle.putInt("fromIntent", 1);
                commonApi.openNewScreen(UpdateGenderAndDietPlanActivity.class, bundle);
                break;
            case R.id.rlBack:
                commonApi.finishActivity(EditProfileActivity.this);
                break;
            case R.id.rlForUserCountry:
                Bundle b1 = new Bundle();
                b1.putString("urlKey", "countries");
                b1.putString("title", "Country");
                commonApi.openNewScreen(CommonListingActivity.class, b1);
                break;
            case R.id.rlForUserStudio:
                Bundle b2 = new Bundle();
                b2.putString("urlKey", "studio");
                b2.putString("title", "Studio");
                commonApi.openNewScreen(CommonListingActivity.class, b2);
                break;
            case R.id.rlForUserTimeZone:
                Bundle b3 = new Bundle();
                b3.putString("urlKey", "timezones");
                b3.putString("title", "Time Zone");
                commonApi.openNewScreen(CommonListingActivity.class, b3);
                break;
        /*    case R.id.rlForUserGroup:
                Bundle b4 = new Bundle();
                b4.putString("urlKey", "groups");
                b4.putString("title", "Group");
                commonApi.openNewScreen(CommonListingActivity.class, b4);
                break;*/
            case R.id.rlForUnit:
                Bundle bundles1 = new Bundle();
                bundles1.putInt("fromIntent", 3);
                commonApi.openNewScreen(UpdateGenderAndDietPlanActivity.class, bundles1);
                break;
            case R.id.rlForAgeRange:
                Bundle b7 = new Bundle();
                b7.putInt("fromIntent", 4);
                commonApi.openNewScreen(UpdateGenderAndDietPlanActivity.class, b7);
                break;
            case R.id.change_bt:
                if(isChange)//button shows edit
                {
                    change_bt.setText("SAVE");
                    isChange=false;
                    imageGallery.setVisibility(View.VISIBLE);
                }
                else//button shows save
                {
                    updateAvatar("avatar",avatarPos);


                }
                break;
          case R.id.rlForUserChallenge:
               Bundle b4 = new Bundle();
               b4.putString("urlKey", "challange");
               b4.putString("title", "Challange");
               commonApi.openNewScreen(CommonListingActivity.class, b4);
            default:
        }

    }

    public void selectAvatar()
    {
        if(loginCredentials.getAvatar()==0)
        {
            imageView.setImageResource(images[0]);
            avatarPos=0;
        }
        else {
            if(loginCredentials.getAvatar()==1) {
                imageView.setImageResource(images[1]);
                avatarPos=1;
            }
            else if(loginCredentials.getAvatar()==2) {
                imageView.setImageResource(images[2]);
                avatarPos=2;
            }
            else if(loginCredentials.getAvatar()==3) {
                imageView.setImageResource(images[3]);
                avatarPos=3;
            }
            else if(loginCredentials.getAvatar()==4) {
                imageView.setImageResource(images[4]);
                avatarPos=4;
            }
            else if(loginCredentials.getAvatar()==5) {
                imageView.setImageResource(images[5]);
                avatarPos=5;
            }
            else if(loginCredentials.getAvatar()==6) {
                imageView.setImageResource(images[6]);
                avatarPos=6;
            }
            else if(loginCredentials.getAvatar()==7) {
                imageView.setImageResource(images[7]);
                avatarPos=7;
            }
            else if(loginCredentials.getAvatar()==8) {
                imageView.setImageResource(images[8]);
                avatarPos=8;
            }
            else if(loginCredentials.getAvatar()==9) {
                imageView.setImageResource(images[9]);
                avatarPos=9;
            }
            else if(loginCredentials.getAvatar()==10) {
                imageView.setImageResource(images[10]);
                avatarPos=10;
            }

        }

        // initialize the adapter
        customGalleryAdapter = new CustomGalleryAdapter(getApplicationContext(), images);

        // set the adapter for gallery
        imageGallery.setAdapter(customGalleryAdapter);

        // Let us do item click of gallery and image can be identified by its position
        imageGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Whichever image is clicked, that is set in the  selectedImageView
                // position will indicate the location of image
                imageView.setImageResource(images[position]);
                avatarPos=position;
            }
        });
    }

    private void updateAvatar(final String urlKey, final int value) {
        if (!commonApi.isInternetAvailable(this)) {
            Toast.makeText(this, Constant.NO_INTERNET, Toast.LENGTH_SHORT).show();
            return;
        }
        commonApi.showProgressDialog("");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", loginCredentials.getUserId());
            jsonObject.put("avatar", value);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        new GetServiceCall(Urls.EDIT_PROFILE_UPDATE + urlKey, GetServiceCall.TYPE_JSONOBJECT_POST, jsonObject) {
            @Override
            public void response(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("sStatus").equalsIgnoreCase("1")) {
                        Toast.makeText(EditProfileActivity.this,"Profile Updated Successfully",Toast.LENGTH_LONG).show();
                        change_bt.setText("EDIT");
                        isChange=true;
                        imageGallery.setVisibility(View.GONE);
                        loginCredentials.setAvatar(value);

                    }


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
    }


