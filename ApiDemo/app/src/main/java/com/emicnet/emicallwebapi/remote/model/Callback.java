package com.emicnet.emicallwebapi.remote.model;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface Callback{

	void onSuccess(JSONObject response);

	void onFailure(VolleyError error);

}
