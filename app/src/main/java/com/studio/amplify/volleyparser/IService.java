package com.studio.amplify.volleyparser;

import com.android.volley.VolleyError;

/**
 * Created by Shubhangi
 */
public interface IService {

    public void response(String response);
    public void error(VolleyError error, String errorMsg);
}
