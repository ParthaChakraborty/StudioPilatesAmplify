package com.studio.amplify.volleyparser;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class GetServiceCall implements IService {

    private static final String TAG = GetServiceCall.class.getSimpleName();
    public static int TYPE_JSONOBJECT = 0;
    public static int TYPE_JSONARRAY = 1;
    public static int TYPE_STRING = 2;
    public static int TYPE_JSONOBJECT_POST = 3;
    public static int TYPE_JSONOBJECT_GET = 4;
    public static int SOCKET_RETRY_TIME = 60000;
    public int type = 0;
    String response = null;
    String errorMsg = "";
    JSONObject object = null;
    private String url;

    public GetServiceCall(String url, int type) {
        super();
        this.url = url;
        this.type = type;
    }

    public GetServiceCall(String url, int type, JSONObject object) {
        super();
        this.url = url;
        this.type = type;
        this.object = object;

    }

    public abstract void response(String response);

    public abstract void error(VolleyError error, String errorMsg);

    // Main implementation of calling the webservice.

    public synchronized final GetServiceCall start() {

        call();

        return this;

    }

    public void call() {

        switch (type) {

            // case  for requesting json object
            case 0:

                JsonObjectRequest request = new JsonObjectRequest(url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject jobj) {

                                response(jobj.toString());

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError e) {

                        error(e, returnErrorMsg(e));
                    }
                });
                request.setRetryPolicy(new DefaultRetryPolicy(
                        GetServiceCall.SOCKET_RETRY_TIME,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                request.setShouldCache(false);
                MyApplication.getInstance().addToRequestQueue(request);

                break;


            // case for requesting json array
            case 1:

                JsonArrayRequest request2 = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray jArray) {

                                response(jArray.toString());
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError arg0) {

                        error(arg0, returnErrorMsg(arg0));
                    }
                });

                request2.setRetryPolicy(new DefaultRetryPolicy(
                        GetServiceCall.SOCKET_RETRY_TIME,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                request2.setShouldCache(false);

                MyApplication.getInstance().addToRequestQueue(request2);

                break;

            case 2:

                break;

            case 3:
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                response(response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error(error, returnErrorMsg(error));
                    }
                });
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        GetServiceCall.SOCKET_RETRY_TIME,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                jsonObjectRequest.setShouldCache(false);
                MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
                break;

        }

    }

    public String returnErrorMsg(VolleyError error) {
        if (error instanceof TimeoutError) {
            errorMsg = "Server Timeout";
        } else if (error instanceof NoConnectionError) {
            errorMsg = "No network connection found";
        } else if (error instanceof AuthFailureError) {
            errorMsg = "Authentication Failure";
        } else if (error instanceof ServerError) {
            errorMsg = "Server down";
        } else if (error instanceof NetworkError) {
            errorMsg = "No internet";
        } else if (error instanceof ParseError) {
            errorMsg = "Parsing Failure";
        } else {
            errorMsg = "No internet";
        }
        return errorMsg;
    }
}
