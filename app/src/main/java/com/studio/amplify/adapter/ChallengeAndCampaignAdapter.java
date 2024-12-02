package com.studio.amplify.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.android.volley.VolleyError;
import com.studio.amplify.R;
import com.studio.amplify.activity.LoginActivity;
import com.studio.amplify.activity.MainActivity;
import com.studio.amplify.model.UpcomingChallengeAndCampaignsItem;
import com.studio.amplify.util.CommonApi;
import com.studio.amplify.util.Constant;
import com.studio.amplify.util.LoadingInterface;
import com.studio.amplify.util.LoginCredentials;
import com.studio.amplify.util.OnItemClick;
import com.studio.amplify.util.Urls;
import com.studio.amplify.volleyparser.GetServiceCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChallengeAndCampaignAdapter extends RecyclerView.Adapter<ChallengeAndCampaignAdapter.MyViewHolder> {
    private List<UpcomingChallengeAndCampaignsItem> upcomingChallengeAndCampaignsItemList;
    private Activity activity;
    private CommonApi commonApi;
    String type, userId;
    private LoadingInterface loadingInterface;

    public ChallengeAndCampaignAdapter(String userId, ArrayList<UpcomingChallengeAndCampaignsItem> upcomingChallengeAndCampaignsItemList, Activity activity, String type, LoadingInterface loadingInterface) {
        this.activity = activity;
        this.type = type;
        this.userId = userId;
        this.upcomingChallengeAndCampaignsItemList = upcomingChallengeAndCampaignsItemList;
        commonApi = new CommonApi(activity);
        this.loadingInterface = loadingInterface;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChallengeAndCampaignAdapter.MyViewHolder holder, final int position) {
        try {
        final UpcomingChallengeAndCampaignsItem upcomingChallengeAndCampaignsItem = upcomingChallengeAndCampaignsItemList.get(position);
        //Glide.with(activity).load(myChallengeAndCampaignsItem.getImage()).into(holder.imageForMealItem);
        holder.tvChlng_cmpn_title.setText(upcomingChallengeAndCampaignsItem.getHeading());

        final SpannableStringBuilder sbFrom = new SpannableStringBuilder(activity.getString(R.string.from_text) + " " + upcomingChallengeAndCampaignsItem.getStartDate());
        final StyleSpan bssFrom = new StyleSpan(android.graphics.Typeface.BOLD);
        sbFrom.setSpan(bssFrom, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        holder.tvFromDateValue.setText(sbFrom);

        final SpannableStringBuilder sbTo = new SpannableStringBuilder(activity.getString(R.string.to_text) + " " + upcomingChallengeAndCampaignsItem.getEndDate());
        final StyleSpan bssTo = new StyleSpan(android.graphics.Typeface.BOLD);
        sbTo.setSpan(bssTo, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        holder.tvToDateValue.setText(sbTo);

      /*  if(upcomingChallengeAndCampaignsItem.getStatus() == 0) {
                holder.btn_Chlng_cmpn_join.setText(R.string.participate);
                holder.btn_Chlng_cmpn_join.setVisibility(View.VISIBLE);
            } else {
                holder.btn_Chlng_cmpn_join.setText("PARTICIPATED");
                holder.btn_Chlng_cmpn_join.setTextColor(Color.BLACK);
                holder.btn_Chlng_cmpn_join.setBackgroundColor(Color.WHITE);
                holder.btn_Chlng_cmpn_join.setEnabled(false);*/

          /*  holder.btn_data_update.setVisibility(View.VISIBLE);
            holder.btn_data_update.setText(R.string.update);*/
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

           /* SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedCurrentDate = df.format(c);*/

          /*  if (isValidDate(upcomingChallengeAndCampaignsItem.getStartDate(), formattedCurrentDate, upcomingChallengeAndCampaignsItem.getEndDate())) {
                holder.btn_Chlng_cmpn_join.setText("PARTICIPATED");
                *//*holder.btn_data_update.setVisibility(View.VISIBLE);
                holder.btn_data_update.setText(R.string.update);*//*
            }

        }*/


        /*holder.btn_Chlng_cmpn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upcomingChallengeAndCampaignsItem.getStatus() == 0) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.myDialog);
                    alertDialogBuilder.setMessage(R.string.participation_text);
                    alertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            participateNow(type, upcomingChallengeAndCampaignsItemList.get(position).getId());
                            loadingInterface.onRedirect(upcomingChallengeAndCampaignsItem, Integer.parseInt(userId), type);
                        }
                    });

                    alertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                } else {
                    loadingInterface.onRedirect(upcomingChallengeAndCampaignsItem, Integer.parseInt(userId), type);

                }
            }
        });*/

        /*holder.btn_data_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btn_data_update.setVisibility(View.VISIBLE);
                loadingInterface.onRedirect(upcomingChallengeAndCampaignsItem, Integer.parseInt(userId), type);
            }
        });*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return upcomingChallengeAndCampaignsItemList.size();
    }

    @NonNull
    @Override
    public ChallengeAndCampaignAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_chlng_cmpn_list_row, parent, false);
        return new ChallengeAndCampaignAdapter.MyViewHolder(v);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvChlng_cmpn_title, tvFromDateValue, tvToDateValue;
        //Button btn_Chlng_cmpn_join btn_data_update;
        CardView cardView;

        private MyViewHolder(View view) {
            super(view);
            tvChlng_cmpn_title = view.findViewById(R.id.tvChlng_cmpn_title);
        //    btn_Chlng_cmpn_join = view.findViewById(R.id.btn_Chlng_cmpn_join);
            cardView = view.findViewById(R.id.cardView);
            tvFromDateValue = view.findViewById(R.id.tvFromDateValue);
            tvToDateValue = view.findViewById(R.id.tvToDateValue);
            //btn_data_update = view.findViewById(R.id.btn_data_update);
        }
    }

    public void participateNow(String type, int camp_chall_id) {
        commonApi.showProgressDialog("Please Wait...");
        JSONObject object = new JSONObject();
        try {
            object.put("user_id", userId);
            object.put("camchall_id", camp_chall_id);
            object.put("camchall_status", type);

            Log.d("TAG", "JSonObject" + object);
            System.out.println(" participateUrl : " + Urls.PARTICIPATE_CAMPAIGNS_CHALLENGES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new GetServiceCall(Urls.PARTICIPATE_CAMPAIGNS_CHALLENGES, GetServiceCall.TYPE_JSONOBJECT_POST, object) {
            @Override
            public void response(String response) {
                Log.d("***participate resp",response);
                commonApi.dismissProgressDialog();
            }

            @Override
            public void error(VolleyError error, String errorMsg) {
                commonApi.dismissProgressDialog();
            }
        }.call();
    }


    public boolean isValidDate(String startDate,String currentDate,String endDate){
        Boolean isValidDates = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date stDate = sdf.parse(startDate);
            Date eDate = sdf.parse(endDate);
            Date currDate = sdf.parse(currentDate);
            if (currDate.before(stDate) && currDate.after(eDate)) {
                isValidDates = false;
            } else {
                isValidDates = true;
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return isValidDates;
    }

}



