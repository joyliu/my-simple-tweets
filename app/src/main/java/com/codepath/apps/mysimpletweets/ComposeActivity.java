package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class ComposeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onSubmit(View view) {
        EditText tvCompose = (EditText) findViewById(R.id.etCompose);
        Intent data = new Intent();
        data.putExtra("text", tvCompose.getText().toString());
        data.putExtra("code", 200);
        setResult(RESULT_OK, data);
        finish();
    }
}
