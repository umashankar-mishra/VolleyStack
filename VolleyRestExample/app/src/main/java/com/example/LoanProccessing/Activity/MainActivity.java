package com.example.LoanProccessing.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.LoanProccessing.AppController.CustomJSONObjectRequest;
import com.example.LoanProccessing.Utils.Constant;
import com.example.LoanProccessing.AppController.CustomVolleyRequestQueue;
import com.example.tudiptechnologies.volleyrestexample.R;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity implements Response.Listener,
        Response.ErrorListener {

    public static final String REQUEST_TAG = "MainActivity";

    private TextView mTextView;
    private Button mButton;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textView);
        mButton = (Button) findViewById(R.id.button);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();

        JSONObject params= new JSONObject();

        try {
            params.put("api_key", Constant.CONST_API_KEY);
            params.put("phone", "0123456789");
            params.put("password", "tudip123");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method
                .POST,Constant.URL_LOGIN,params
                , this, this);
        jsonRequest.setTag(REQUEST_TAG);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQueue.add(jsonRequest);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mTextView.setText(error.getMessage());
    }

    @Override
    public void onResponse(Object response) {
        mTextView.setText("Response is: " + response);
        try {
            mTextView.setText(mTextView.getText() + "\n\n" );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}