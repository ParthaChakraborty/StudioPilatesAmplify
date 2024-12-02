package com.studio.amplify.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.studio.amplify.R;
import com.studio.amplify.fragment.CampaignListFragment;
import com.studio.amplify.fragment.FeedFragment;
import com.studio.amplify.interfaces.ParticipateClick;
import com.studio.amplify.model.ChallengeCampaignListItem;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.LoadingInterface;
import com.studio.amplify.util.LoginCredentials;
import com.studio.amplify.util.OnClassItemClick;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;
import com.studio.amplify.volleyparser.MyApplication;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChallengeCampaignListAdapter extends RecyclerView.Adapter<ChallengeCampaignListAdapter.MyViewHolder> {
    private List<ChallengeCampaignListItem> challengeListItems;
    private Activity activity;
    private CommonApi commonApi;
    LoadingInterface loadingInterface;
    String userId;
    String type;
    private LoginCredentials loginCredentials;
    String selected_class="";
    int year, month, dayOfMonth;
    private ParticipateClick participateClick;

    public ChallengeCampaignListAdapter(ArrayList<ChallengeCampaignListItem> challengeListItems, Activity activity, LoadingInterface loadingInterface, String userId, String type, ParticipateClick listener) {
        this.challengeListItems = challengeListItems;
        this.activity = activity;
        this.loadingInterface = loadingInterface;
        this.type = type;
        this.userId = userId;
        commonApi = new CommonApi(activity);
        loginCredentials = LoginCredentials.getInstance(activity);
        this.participateClick = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.challenge_campaign_list_item_row, parent, false);
        return new MyViewHolder(v);

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ChallengeCampaignListItem challengeListItem = challengeListItems.get(position);
        holder.tvChlngCmpgn_title.setText(challengeListItem.getHeading());
        String classOpted = challengeListItem.getClassBuy();
        String noOfClasses = challengeListItem.getClassOpted();
        /*if(classOpted.equals("")) {
            holder.tvClassOpted.setVisibility(View.GONE);
            holder.tvNoOfClass.setVisibility(View.GONE);
            holder.btn_classes.setVisibility(View.VISIBLE);
        } else {
            holder.tvClassOpted.setText("No of Classes Opted:" + classOpted);
            holder.tvNoOfClass.setText("No of Classes:" + noOfClasses);

        }*/

        final SpannableStringBuilder sbFrom = new SpannableStringBuilder(activity.getString(R.string.from_text) + " " + challengeListItem.getStartDate());
        final StyleSpan bssFrom = new StyleSpan(android.graphics.Typeface.BOLD);
        sbFrom.setSpan(bssFrom, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        holder.tvChlngCmpgnFromDateValue.setText(sbFrom);

        final SpannableStringBuilder sbTo = new SpannableStringBuilder(activity.getString(R.string.to_text) + " " + challengeListItem.getEndDate());
        final StyleSpan bssTo = new StyleSpan(android.graphics.Typeface.BOLD);
        sbTo.setSpan(bssTo, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        holder.tvChlngCmpgnToDateValue.setText(sbTo);

        holder.btn_classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callClassList();
            }
        });



        /*holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingInterface.onRedirectFromMyCampaignsAndChallenges(challengeListItem,Integer.parseInt(userId),type);
            }
        });*/


        Calendar calendar = Calendar.getInstance();
        //Date currdate = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date date = sdf.parse(challengeListItem.getEndDate());
            String cur_da = loginCredentials.getCurrentDateFromFeed();
            Date currdate = sdf.parse(cur_da);
            System.out.println(date);
            if(currdate.after(date)) {
                holder.tvClassOpted.setVisibility(View.GONE);
                holder.tvNoOfClass.setVisibility(View.GONE);
                holder.btn_classes.setVisibility(View.GONE);
                holder.btn_updte.setVisibility(View.VISIBLE);
            } else {
                if(classOpted.equals("")) {
                    holder.tvClassOpted.setVisibility(View.GONE);
                    holder.tvNoOfClass.setVisibility(View.GONE);
                    holder.btn_classes.setVisibility(View.VISIBLE);
                    holder.btn_updte.setVisibility(View.VISIBLE);
                } else {
                    holder.tvClassOpted.setText("No. of Classes Opted:" + classOpted);
                    holder.tvNoOfClass.setText("No. of Classes Completed:" + noOfClasses);
                    holder.btn_classes.setVisibility(View.GONE);
                    holder.btn_updte.setVisibility(View.VISIBLE);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.btn_updte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingInterface.onRedirectFromMyCampaignsAndChallenges(challengeListItem,Integer.parseInt(userId), type);
            }
        });

    }

    public void callClassList() {
        commonApi.showProgressDialog("Please Wait...");
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetDialog.setContentView(R.layout.classlist_view);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        RecyclerView rvForClassList = bottomSheetDialog.findViewById(R.id.rvForClassList);
        ImageView img_close = bottomSheetDialog.findViewById(R.id.img_close);

        String s = Urls.GET_CLASS_LIST+loginCredentials.getCAMP_CHAL_ID()+"/"+type;
        List<String> classArrayList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, s,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            commonApi.dismissProgressDialog();
                            JSONObject jsonObject = new JSONObject(response);
                            String listValues = jsonObject.getString("classlists");
                            JSONArray listItems = new JSONArray(listValues);

                            for (int i = 0; i < listItems.length(); i++) {
                                String str = listItems.getString(i);
                                classArrayList.add(str);
                            }
                            if(classArrayList.size()>0 && !classArrayList.get(0).equals("")) {
                                LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
                                rvForClassList.setLayoutManager(layoutManager);
                                ChCaClassListAdapter classListAdapter = new ChCaClassListAdapter(classArrayList, activity);
                                rvForClassList.setAdapter(classListAdapter);
                                bottomSheetDialog.show();
                            } else {
                                bottomSheetDialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        commonApi.dismissProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                commonApi.dismissProgressDialog();
            }
        });
        MyApplication.getInstance().addToRequestQueue(stringRequest);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                participateNow(type, loginCredentials.getCAMP_CHAL_ID(), Integer.parseInt(loginCredentials.getUserGroup()),selected_class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return challengeListItems.size();
    }

    public void participateNow(String type, int camp_chall_id, final int id_group, String sel_class) {
        commonApi.showProgressDialog("Please Wait...");
        JSONObject object = new JSONObject();
        try {
            object.put("user_id", loginCredentials.getUserId());
            object.put("camchall_id", camp_chall_id);
            object.put("camchall_status", type);
            object.put("group_id", id_group);
            object.put("selected_class",sel_class);
            Log.d("ttttt:",sel_class);
            Log.d("TAG", "parObject" + object);
            System.out.println(" participateUrl : " + Urls.PARTICIPATE_CAMPAIGNS_CHALLENGES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.PARTICIPATE_CAMPAIGNS_CHALLENGES, GetServiceCall.TYPE_JSONOBJECT_POST, object) {
            @Override
            public void response(String response) {
                Log.d("***participate resp", response);
                try {
                    commonApi.dismissProgressDialog();
                    JSONObject jsonObject = new JSONObject(response);
                   // String group_name = jsonObject.getString("group_name");
                    loginCredentials.setUserGroup(String.valueOf(id_group));
                   // loginCredentials.setUserGroupName(group_name);
                    participateClick.onItemClick();

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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvChlngCmpgn_title, tvChlngCmpgnFromDateValue, tvChlngCmpgnToDateValue,
                btn_updte, tvNoOfClass, btn_classes, tvClassOpted;
        CardView cardView;

        public MyViewHolder(@NonNull View view) {
            super(view);
            tvChlngCmpgn_title = view.findViewById(R.id.tvChlngCmpgn_title);
            tvChlngCmpgnFromDateValue = view.findViewById(R.id.tvChlngCmpgnFromDateValue);
            tvChlngCmpgnToDateValue = view.findViewById(R.id.tvChlngCmpgnToDateValue);
            cardView = view.findViewById(R.id.cardView);
            btn_updte = view.findViewById(R.id.btn_updte);
            tvNoOfClass = view.findViewById(R.id.tvNoOfClass);
            btn_classes = view.findViewById(R.id.btn_classes);
            tvClassOpted = view.findViewById(R.id.tvClassOpted);
        }
    }

    public class ChCaClassListAdapter extends RecyclerView.Adapter<ChCaClassListAdapter.MyViewHolder> {

        private List<String> classListItem;
        private Activity activity;
        private int selectedPosition = -1;

        public ChCaClassListAdapter(List<String> classListItem, Activity activity) {
            this.activity = activity;
            this.classListItem = classListItem;
        }

        @Override
        public void onBindViewHolder(@NonNull final ChCaClassListAdapter.MyViewHolder holder, final int position) {
            final String classListingItemObject = classListItem.get(position);

            holder.tvClassName.setText(classListingItemObject);

            if (selectedPosition == position) {
                holder.imgEnable.setVisibility(View.VISIBLE);
                selected_class = classListingItemObject;
                Log.d("Selected_Class",selected_class);
            } else {
                holder.imgEnable.setVisibility(View.GONE);
            }


            holder.tvClassName.setOnClickListener(view -> {
                if (selectedPosition >= 0)
                    notifyItemChanged(selectedPosition);
                holder.imgEnable.setVisibility(View.VISIBLE);
                selected_class = classListingItemObject;
                Log.d("Selected_Class",selected_class);
                selectedPosition = holder.getAdapterPosition();
            });


        }

        @Override
        public int getItemCount() {
            return classListItem.size();
        }

        @NonNull
        @Override
        public ChCaClassListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.classlist_item_view, parent, false);
            return new ChCaClassListAdapter.MyViewHolder(v);
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvClassName;
            ImageView imgEnable;


            private MyViewHolder(View view) {
                super(view);
                tvClassName = view.findViewById(R.id.tvClassName);
                imgEnable   = view.findViewById(R.id.imgEnable);

            }
        }
    }
}



