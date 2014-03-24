package com.twitter.android.yamba;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class TweetActivity extends Activity
        implements View.OnClickListener {

    private static final String TAG = "TweetActivity";
    EditText mEditMsg;
    Toast mToast;

    YambaClient mYambaClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate() invoked");

        setContentView(R.layout.activity_tweet);

        mToast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_LONG);
        mEditMsg = (EditText) findViewById(R.id.edit_msg);
        Button buttonTweet = (Button) findViewById(R.id.button_tweet);
        buttonTweet.setOnClickListener(this);

        mYambaClient = new YambaClient("student", "password");
    }

    public void showToast(int stringId) {
        mToast.setText(stringId);
        mToast.show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_tweet:
                if (BuildConfig.DEBUG) Log.d(TAG, "Tweet button clicked");

                String msg = mEditMsg.getText().toString();
                if (!TextUtils.isEmpty(msg)) {
                    Log.d(TAG, "User entered: " + msg);
                    mEditMsg.setText("");
                    new PostTweetTask().execute(msg);
                }
                break;
            default:
                // Unknown button clicked?
                Log.w(TAG, "What was that?");
        }
    }

    class PostTweetTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            int result = R.string.post_tweet_fail;
            if (params.length > 0 && !TextUtils.isEmpty(params[0])) {
                try {
                    mYambaClient.postStatus(params[0]);
                    result = R.string.post_tweet_success;
                } catch (YambaClientException e) {
                    Log.e(TAG, "Failed to post status", e);
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            showToast(result);
        }
    }
}
