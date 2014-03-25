package com.twitter.android.yamba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TweetActivity extends Activity
        implements TweetFragment.OnTweetPostedListener {

    private static final String TAG = "TweetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate() invoked");

        setContentView(R.layout.activity_tweet);
        TweetFragment f = (TweetFragment) getFragmentManager().findFragmentById(R.id.fragment_tweet);
        f.setOnTweetPostedListener(this);
    }

    @Override
    public void onTweetPosted() {
        finish();
    }
}
