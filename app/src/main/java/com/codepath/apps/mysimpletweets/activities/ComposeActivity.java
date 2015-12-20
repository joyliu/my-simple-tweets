package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.network.TwitterApplication;
import com.codepath.apps.mysimpletweets.network.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeActivity extends AppCompatActivity {

    private TwitterClient client;
    private Button btnCompose;
    private EditText etCompose;
    private TextView tvCharCount;
    private String status;
    private Tweet newTweet = new Tweet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        client = TwitterApplication.getRestClient();
        btnCompose = (Button) findViewById(R.id.btnCompose);
        etCompose = (EditText) findViewById(R.id.etCompose);
        tvCharCount = (TextView) findViewById(R.id.tvCharCount);
        charCount();
    }

    public void onSubmit(View view) {
        status = etCompose.getText().toString();
        postTweet();
    }

    private void charCount() {
        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)
                int charCount = s.length();

                charCount = 140 - charCount;

                tvCharCount.setText(String.valueOf(charCount));

                if (charCount < 0) {
                    tvCharCount.setTextColor(Color.RED);
                    btnCompose.setEnabled(false);
                } else {
                    tvCharCount.setTextColor(ContextCompat.getColor(getApplicationContext(),
                            R.color.accent_material_light));
                    btnCompose.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Fires right before text is changing
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed
            }
        });
    }

    // Send an API request to post the status json
    private void postTweet() {
        client.composeTweet(status, new JsonHttpResponseHandler() {
            // Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                newTweet = Tweet.fromJSON(response);

                Intent data = new Intent();
                data.putExtra("newTweet", newTweet);
                setResult(RESULT_OK, data);
                finish();
            }

            // Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}
